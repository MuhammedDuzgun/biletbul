package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.User;
import com.staj.biletbul.request.CreateUserRequest;
import com.staj.biletbul.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail()
        );
        return userResponse;
    }

    public User mapToEntity(CreateUserRequest request) {
        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        return user;
    }

}
