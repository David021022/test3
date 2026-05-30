package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaperQuestionView {
    private Long questionId;
    private Integer score;
    private Integer sortOrder;
}

