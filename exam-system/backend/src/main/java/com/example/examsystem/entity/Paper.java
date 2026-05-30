package com.example.examsystem.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "paper")
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(length = 500)
    private String description;
}

