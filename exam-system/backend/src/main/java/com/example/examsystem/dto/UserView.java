package com.example.examsystem.dto;

import com.example.examsystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserView {
    private Long id;
    private String username;
    private Role role;
    private Boolean status;
}

