package com.example.examsystem.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ExamAnswerRequest {
    @NotNull
    private Long questionId;
    @NotBlank
    private String answerContent;
}

