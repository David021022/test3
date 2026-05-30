package com.example.examsystem.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExamSubmitRequest {
    @NotNull
    private Long examId;
    @NotEmpty
    private List<ExamAnswerRequest> answers = new ArrayList<>();
}

