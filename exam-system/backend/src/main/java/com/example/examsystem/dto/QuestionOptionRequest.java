package com.example.examsystem.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class QuestionOptionRequest {
    @NotBlank
    private String optionKey;
    @NotBlank
    private String content;
    @NotNull
    private Boolean correct;
}

