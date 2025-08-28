package com.staj.biletbul.controller;

import com.staj.biletbul.request.LoginRequest;
import com.staj.biletbul.request.SignupRequest;
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
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String message = authService.login(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        String message = authService.signup(request);
        return ResponseEntity.ok(message);
    }

}
