package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TeacherResultView {
    private Long recordId;
    private Long studentId;
    private String studentUsername;
    private Integer totalScore;
    private LocalDateTime submittedAt;
}

