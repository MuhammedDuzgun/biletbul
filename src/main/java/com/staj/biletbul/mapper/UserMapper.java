package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.User;
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

}
