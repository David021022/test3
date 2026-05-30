package com.example.examsystem.repository;

import com.example.examsystem.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    Optional<ExamResult> findByRecordId(Long recordId);
    List<ExamResult> findByRecordIdIn(List<Long> recordIds);
}

