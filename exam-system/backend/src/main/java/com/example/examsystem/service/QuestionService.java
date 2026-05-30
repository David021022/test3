package com.example.examsystem.service;

import com.example.examsystem.common.BizException;
import com.example.examsystem.dto.*;
import com.example.examsystem.entity.Question;
import com.example.examsystem.entity.QuestionOption;
import com.example.examsystem.repository.QuestionOptionRepository;
import com.example.examsystem.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository optionRepository;

    public QuestionService(QuestionRepository questionRepository, QuestionOptionRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    public List<QuestionView> list() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> ids = questions.stream().map(Question::getId).collect(Collectors.toList());
        Map<Long, List<QuestionOptionView>> optionMap = optionRepository.findByQuestionIdIn(ids).stream()
                .collect(Collectors.groupingBy(QuestionOption::getQuestionId,
                        Collectors.mapping(o -> new QuestionOptionView(o.getOptionKey(), o.getContent(), o.getCorrect()), Collectors.toList())));

        return questions.stream()
                .map(q -> new QuestionView(q.getId(), q.getCourseId(), q.getTitle(), q.getType(), q.getAnswer(), q.getAnalysis(),
                        optionMap.getOrDefault(q.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionView create(QuestionRequest req) {
        Question q = new Question();
        q.setCourseId(req.getCourseId());
        q.setTitle(req.getTitle());
        q.setType(req.getType());
        q.setAnswer(normalizeAnswer(req.getType().name(), req.getAnswer()));
        q.setAnalysis(req.getAnalysis());
        questionRepository.save(q);

        List<QuestionOptionView> options = saveOptions(q.getId(), req.getOptions());
        return new QuestionView(q.getId(), q.getCourseId(), q.getTitle(), q.getType(), q.getAnswer(), q.getAnalysis(), options);
    }

    @Transactional
    public QuestionView update(Long id, QuestionRequest req) {
        Question q = questionRepository.findById(id).orElseThrow(() -> new BizException(404, "question not found"));
        q.setCourseId(req.getCourseId());
        q.setTitle(req.getTitle());
        q.setType(req.getType());
        q.setAnswer(normalizeAnswer(req.getType().name(), req.getAnswer()));
        q.setAnalysis(req.getAnalysis());
        questionRepository.save(q);

        optionRepository.deleteByQuestionId(q.getId());
        List<QuestionOptionView> options = saveOptions(q.getId(), req.getOptions());
        return new QuestionView(q.getId(), q.getCourseId(), q.getTitle(), q.getType(), q.getAnswer(), q.getAnalysis(), options);
    }

    @Transactional
    public void delete(Long id) {
        optionRepository.deleteByQuestionId(id);
        questionRepository.deleteById(id);
    }

    private List<QuestionOptionView> saveOptions(Long questionId, List<QuestionOptionRequest> options) {
        List<QuestionOptionView> result = new ArrayList<>();
        if (options == null) {
            return result;
        }
        for (QuestionOptionRequest item : options) {
            QuestionOption option = new QuestionOption();
            option.setQuestionId(questionId);
            option.setOptionKey(item.getOptionKey());
            option.setContent(item.getContent());
            option.setCorrect(item.getCorrect());
            optionRepository.save(option);
            result.add(new QuestionOptionView(option.getOptionKey(), option.getContent(), option.getCorrect()));
        }
        return result;
    }

    public static String normalizeAnswer(String type, String raw) {
        if (raw == null) {
            return "";
        }
        String answer = raw.trim().replace("，", ",").toUpperCase();
        if ("MULTIPLE".equals(type)) {
            String[] parts = answer.split(",");
            List<String> cleaned = Arrays.stream(parts)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            return String.join(",", cleaned);
        }
        return answer;
    }
}
