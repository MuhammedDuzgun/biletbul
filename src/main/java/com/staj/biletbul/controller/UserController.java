package com.staj.biletbul.controller;

import com.staj.biletbul.response.AllEventsOfUserResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.TicketResponse;
import com.staj.biletbul.response.UserResponse;
import com.staj.biletbul.security.CustomUserDetails;
import com.staj.biletbul.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketResponse>> getAllTicketsOfUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getAllTicketsOfUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteUserById(@AuthenticationPrincipal
                                                                      CustomUserDetails customUserDetails,
                                                                  @PathVariable("id") Long id)
            throws AccessDeniedException {
        return new ResponseEntity<>(userService.deleteUserById(id, customUserDetails), HttpStatus.ACCEPTED);
    }

}
