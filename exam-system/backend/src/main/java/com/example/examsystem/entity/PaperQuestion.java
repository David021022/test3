package com.example.examsystem.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "paper_question")
public class PaperQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paper_id", nullable = false)
    private Long paperId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Integer score;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}

