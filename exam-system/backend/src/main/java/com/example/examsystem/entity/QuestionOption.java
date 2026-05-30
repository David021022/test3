package com.example.examsystem.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "question_option")
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "option_key", nullable = false, length = 5)
    private String optionKey;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "is_correct", nullable = false)
    private Boolean correct;
}

