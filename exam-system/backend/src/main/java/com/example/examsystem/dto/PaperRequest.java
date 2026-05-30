package com.example.examsystem.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaperRequest {
    @NotBlank
    private String name;
    @NotNull
    private Long courseId;
    private String description;
    private List<PaperQuestionRequest> questions = new ArrayList<>();
}

