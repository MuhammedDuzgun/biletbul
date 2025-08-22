package com.staj.biletbul.service;

import com.staj.biletbul.entity.Role;
import com.staj.biletbul.entity.User;
import com.staj.biletbul.enums.RoleName;
import com.staj.biletbul.exception.UserAlreadyExistsException;
import com.staj.biletbul.exception.UserNotFoundException;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.UserMapper;
import com.staj.biletbul.repository.RoleRepository;
import com.staj.biletbul.repository.SeatRepository;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.request.CreateUserRequest;
import com.staj.biletbul.response.AllEventsOfUserResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final SeatRepository seatRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       EventMapper eventMapper,
                       SeatRepository seatRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.eventMapper = eventMapper;
        this.seatRepository = seatRepository;
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> userResponses = userRepository.findAll()
                .stream()
                .map(userMapper::mapToUserResponse)
                .toList();
        return userResponses;
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
        return userMapper.mapToUserResponse(user);
    }

    public AllEventsOfUserResponse getAllEventsOfUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));

        List<EventResponse> events = user.getEvents()
                .stream()
                .map(eventMapper::mapToResponse)
                .toList();

        AllEventsOfUserResponse response = new AllEventsOfUserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                events
        );
        return response;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("user already exists with email: " + request.email());
        }

        //rol
        Set<Role> roles = new HashSet<>();
        Role roleOrganizer = roleRepository.findByRoleName(RoleName.ROLE_USER);
        roles.add(roleOrganizer);

        User userToCreate = userMapper.mapToEntity(request);
        userToCreate.setRoles(roles);

        return userMapper.mapToUserResponse(userRepository.save(userToCreate));
    }

    @Transactional
    public ResourceDeletedResponse deleteUserById(Long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));

        //event_users tablosundan sil
        userRepository.deleteUserEvents(id);

        //user_roles temizle
        userToDelete.getRoles().clear();

        //kullanıcının koltuklarını sil
        seatRepository.deleteSeatsByUserId(id);

        //user'ı sil
        userRepository.deleteUserById(id);

        return new ResourceDeletedResponse("Deleted user with id: " + id);
    }

}
