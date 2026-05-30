package com.example.examsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExamView {
    private Long id;
    private String name;
    private Long courseId;
    private Long paperId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

