package com.example.examsystem.repository;

import com.example.examsystem.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    List<QuestionOption> findByQuestionIdIn(List<Long> questionIds);
    List<QuestionOption> findByQuestionId(Long questionId);
    void deleteByQuestionId(Long questionId);
}

