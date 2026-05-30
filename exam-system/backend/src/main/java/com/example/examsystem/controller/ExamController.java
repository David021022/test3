package com.example.examsystem.controller;

import com.example.examsystem.common.ApiResponse;
import com.example.examsystem.dto.ExamAnswerRequest;
import com.example.examsystem.dto.ExamRequest;
import com.example.examsystem.dto.ExamSubmitResponse;
import com.example.examsystem.dto.ExamView;
import com.example.examsystem.security.AuthUser;
import com.example.examsystem.security.SecurityUtils;
import com.example.examsystem.service.ExamService;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/exams")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ApiResponse<List<ExamView>> list() {
        return ApiResponse.ok(examService.list());
    }

    @PostMapping("/exams")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ApiResponse<ExamView> create(@Validated @RequestBody ExamRequest req) {
        return ApiResponse.ok(examService.create(req));
    }

    @GetMapping("/student/exams")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<List<ExamView>> listForStudent() {
        return ApiResponse.ok(examService.listForStudent());
    }

    @PostMapping("/student/exams/{examId}/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<ExamSubmitResponse> submit(@PathVariable Long examId, @Validated @RequestBody SubmitBody body) {
        AuthUser user = SecurityUtils.currentUser();
        return ApiResponse.ok(examService.submit(user.getUserId(), examId, body.getAnswers()));
    }

    @GetMapping("/student/results")
    @PreAuthorize("hasRole('STUDENT')")
    public ApiResponse<List<Map<String, Object>>> studentResults() {
        AuthUser user = SecurityUtils.currentUser();
        return ApiResponse.ok(examService.studentResults(user.getUserId()));
    }

    @GetMapping("/teacher/exams/{examId}/results")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ApiResponse<List<Map<String, Object>>> teacherResults(@PathVariable Long examId) {
        return ApiResponse.ok(examService.teacherResults(examId));
    }

    @Data
    public static class SubmitBody {
        @NotEmpty
        private List<ExamAnswerRequest> answers;
    }
}

