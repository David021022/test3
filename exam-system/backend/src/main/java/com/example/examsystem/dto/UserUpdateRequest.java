package com.example.examsystem.dto;

import com.example.examsystem.entity.Role;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class UserUpdateRequest {
    @NotNull
    private Role role;
    @NotNull
    private Boolean status;
}

