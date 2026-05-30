package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentResultView {
    private Long recordId;
    private Long examId;
    private String examName;
    private Integer totalScore;
    private LocalDateTime submittedAt;
}

