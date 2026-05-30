package com.example.examsystem.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class CourseRequest {
    @NotBlank
    private String name;
    private String description;
}

