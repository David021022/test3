package com.example.examsystem.controller;

import com.example.examsystem.common.ApiResponse;
import com.example.examsystem.dto.LoginRequest;
import com.example.examsystem.dto.LoginResponse;
import com.example.examsystem.service.AuthService;
import org.springframework.validation.annotation.Validated;
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
    public ApiResponse<LoginResponse> login(@Validated @RequestBody LoginRequest req) {
        return ApiResponse.ok(authService.login(req.getUsername(), req.getPassword()));
    }
}

