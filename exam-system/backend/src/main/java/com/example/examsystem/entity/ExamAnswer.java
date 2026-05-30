package com.example.examsystem.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "exam_answer")
public class ExamAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "answer_content", nullable = false, length = 500)
    private String answerContent;
}

