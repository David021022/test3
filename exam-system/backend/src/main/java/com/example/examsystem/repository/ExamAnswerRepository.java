package com.example.examsystem.repository;

import com.example.examsystem.entity.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {
    List<ExamAnswer> findByRecordId(Long recordId);
    void deleteByRecordId(Long recordId);
}

