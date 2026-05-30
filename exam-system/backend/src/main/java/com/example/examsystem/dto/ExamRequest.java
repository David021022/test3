package com.example.examsystem.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ExamRequest {
    @NotBlank
    private String name;
    @NotNull
    private Long courseId;
    @NotNull
    private Long paperId;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}

