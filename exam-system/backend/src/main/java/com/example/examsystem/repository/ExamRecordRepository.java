package com.example.examsystem.repository;

import com.example.examsystem.entity.ExamRecord;
import com.example.examsystem.entity.ExamRecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
    Optional<ExamRecord> findByExamIdAndStudentId(Long examId, Long studentId);
    List<ExamRecord> findByStudentId(Long studentId);
    List<ExamRecord> findByExamIdAndStatus(Long examId, ExamRecordStatus status);
}

