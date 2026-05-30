package com.example.examsystem.service;

import com.example.examsystem.common.BizException;
import com.example.examsystem.dto.*;
import com.example.examsystem.entity.Paper;
import com.example.examsystem.entity.PaperQuestion;
import com.example.examsystem.repository.PaperQuestionRepository;
import com.example.examsystem.repository.PaperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperService {
    private final PaperRepository paperRepository;
    private final PaperQuestionRepository paperQuestionRepository;

    public PaperService(PaperRepository paperRepository, PaperQuestionRepository paperQuestionRepository) {
        this.paperRepository = paperRepository;
        this.paperQuestionRepository = paperQuestionRepository;
    }

    public List<PaperView> list() {
        return paperRepository.findAll().stream().map(this::toView).collect(Collectors.toList());
    }

    @Transactional
    public PaperView create(PaperRequest req) {
        Paper p = new Paper();
        p.setName(req.getName());
        p.setCourseId(req.getCourseId());
        p.setDescription(req.getDescription());
        paperRepository.save(p);
        savePaperQuestions(p.getId(), req.getQuestions());
        return toView(p);
    }

    @Transactional
    public PaperView update(Long id, PaperRequest req) {
        Paper p = paperRepository.findById(id).orElseThrow(() -> new BizException(404, "paper not found"));
        p.setName(req.getName());
        p.setCourseId(req.getCourseId());
        p.setDescription(req.getDescription());
        paperRepository.save(p);

        paperQuestionRepository.deleteByPaperId(id);
        savePaperQuestions(id, req.getQuestions());
        return toView(p);
    }

    @Transactional
    public void delete(Long id) {
        paperQuestionRepository.deleteByPaperId(id);
        paperRepository.deleteById(id);
    }

    private void savePaperQuestions(Long paperId, List<PaperQuestionRequest> questions) {
        if (questions == null) {
            return;
        }
        for (PaperQuestionRequest q : questions) {
            PaperQuestion pq = new PaperQuestion();
            pq.setPaperId(paperId);
            pq.setQuestionId(q.getQuestionId());
            pq.setScore(q.getScore());
            pq.setSortOrder(q.getSortOrder());
            paperQuestionRepository.save(pq);
        }
    }

    private PaperView toView(Paper p) {
        List<PaperQuestionView> questions = paperQuestionRepository.findByPaperIdOrderBySortOrderAsc(p.getId()).stream()
                .map(i -> new PaperQuestionView(i.getQuestionId(), i.getScore(), i.getSortOrder()))
                .collect(Collectors.toList());
        return new PaperView(p.getId(), p.getName(), p.getCourseId(), p.getDescription(), questions);
    }
}
