package com.staj.biletbul.service;

import com.staj.biletbul.entity.User;
import com.staj.biletbul.exception.UserAlreadyExistsException;
import com.staj.biletbul.exception.UserNotFoundException;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.UserMapper;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.request.CreateUserRequest;
import com.staj.biletbul.response.AllEventsOfUserResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;


    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       EventMapper eventMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.eventMapper = eventMapper;
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
        return userMapper.mapToUserResponse(userRepository.save(userMapper.mapToEntity(request)));
    }

    @Transactional
    public ResourceDeletedResponse deleteUserById(Long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));

        //event_users tablosundan sil
        userRepository.deleteUserEvents(id);

        //user'ı sil
        userRepository.deleteUserById(id);

        return new ResourceDeletedResponse("Deleted user with id: " + id);
    }

}
