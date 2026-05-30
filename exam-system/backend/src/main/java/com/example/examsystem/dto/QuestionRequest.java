package com.example.examsystem.dto;

import com.example.examsystem.entity.QuestionType;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionRequest {
    @NotNull
    private Long courseId;
    @NotBlank
    private String title;
    @NotNull
    private QuestionType type;
    @NotBlank
    private String answer;
    private String analysis;
    private List<QuestionOptionRequest> options = new ArrayList<>();
}

