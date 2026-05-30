package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionOptionView {
    private String optionKey;
    private String content;
    private Boolean correct;
}

