package com.example.examsystem.dto;

import com.example.examsystem.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionView {
    private Long id;
    private Long courseId;
    private String title;
    private QuestionType type;
    private String answer;
    private String analysis;
    private List<QuestionOptionView> options;
}

