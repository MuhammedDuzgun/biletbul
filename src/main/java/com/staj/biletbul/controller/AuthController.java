package com.staj.biletbul.controller;

import com.staj.biletbul.request.LoginRequest;
import com.staj.biletbul.request.SignupAsOrganizerRequest;
import com.staj.biletbul.request.SignupAsUserRequest;
import com.staj.biletbul.response.JwtAuthResponse;
import com.staj.biletbul.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/user")
    public ResponseEntity<String> signupAsUser(@RequestBody SignupAsUserRequest request) {
        String message = authService.signupAsUser(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/signup/organizer")
    public ResponseEntity<String> signupAsOrganizer(@RequestBody SignupAsOrganizerRequest request) {
        String message = authService.signupAsOrganizer(request);
        return ResponseEntity.ok(message);
    }
}
