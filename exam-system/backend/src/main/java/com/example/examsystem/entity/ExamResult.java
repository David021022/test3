package com.example.examsystem.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "exam_result")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_id", nullable = false, unique = true)
    private Long recordId;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;
}

