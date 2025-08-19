package com.staj.biletbul.service;

import com.staj.biletbul.entity.User;
import com.staj.biletbul.exception.UserNotFoundException;
import com.staj.biletbul.mapper.UserMapper;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream().map(
                userMapper::mapToUserResponse
        ).toList();
        return userResponses;
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    public UserResponse createUser(User user) {
        User createdUser = userRepository.save(user);
        return userMapper.mapToUserResponse(createdUser);
    }

    @Transactional
    public ResourceDeletedResponse deleteUserById(Long id) {
        userRepository.deleteById(id);
        return new ResourceDeletedResponse("Deleted user with id: " + id);
    }

}
