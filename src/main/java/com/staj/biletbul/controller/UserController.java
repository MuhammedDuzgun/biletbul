package com.staj.biletbul.controller;

import com.staj.biletbul.request.CreateUserRequest;
import com.staj.biletbul.response.AllEventsOfUserResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.UserResponse;
import com.staj.biletbul.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<AllEventsOfUserResponse> getAllEventsOfUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getAllEventsOfUser(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.ACCEPTED);
    }

}
