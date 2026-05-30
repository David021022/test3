package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamSubmitResponse {
    private Long recordId;
    private Integer totalScore;
}

