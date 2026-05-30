package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaperView {
    private Long id;
    private String name;
    private Long courseId;
    private String description;
    private List<PaperQuestionView> questions;
}

