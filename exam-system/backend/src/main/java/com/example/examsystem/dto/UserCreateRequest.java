package com.example.examsystem.dto;

import com.example.examsystem.entity.Role;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class UserCreateRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Role role;
    @NotNull
    private Boolean status;
}

