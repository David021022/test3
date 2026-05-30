package com.example.examsystem.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class PaperQuestionRequest {
    @NotNull
    private Long questionId;
    @NotNull
    private Integer score;
    @NotNull
    private Integer sortOrder;
}

