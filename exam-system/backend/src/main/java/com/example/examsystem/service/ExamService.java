package com.example.examsystem.service;

import com.example.examsystem.common.BizException;
import com.example.examsystem.dto.ExamAnswerRequest;
import com.example.examsystem.dto.ExamRequest;
import com.example.examsystem.dto.ExamSubmitResponse;
import com.example.examsystem.dto.ExamView;
import com.example.examsystem.entity.*;
import com.example.examsystem.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final PaperRepository paperRepository;
    private final PaperQuestionRepository paperQuestionRepository;
    private final QuestionRepository questionRepository;
    private final SysUserRepository userRepository;
    private final ExamRecordRepository recordRepository;
    private final ExamAnswerRepository answerRepository;
    private final ExamResultRepository resultRepository;

    public ExamService(ExamRepository examRepository,
                       PaperRepository paperRepository,
                       PaperQuestionRepository paperQuestionRepository,
                       QuestionRepository questionRepository,
                       SysUserRepository userRepository,
                       ExamRecordRepository recordRepository,
                       ExamAnswerRepository answerRepository,
                       ExamResultRepository resultRepository) {
        this.examRepository = examRepository;
        this.paperRepository = paperRepository;
        this.paperQuestionRepository = paperQuestionRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.answerRepository = answerRepository;
        this.resultRepository = resultRepository;
    }

    public List<ExamView> list() {
        return examRepository.findAll().stream()
                .map(e -> new ExamView(e.getId(), e.getName(), e.getCourseId(), e.getPaperId(), e.getStartTime(), e.getEndTime()))
                .collect(Collectors.toList());
    }

    public ExamView create(ExamRequest req) {
        if (req.getEndTime().isBefore(req.getStartTime()) || req.getEndTime().isEqual(req.getStartTime())) {
            throw new BizException(400, "end time must be after start time");
        }
        paperRepository.findById(req.getPaperId()).orElseThrow(() -> new BizException(404, "paper not found"));
        Exam exam = new Exam();
        exam.setName(req.getName());
        exam.setCourseId(req.getCourseId());
        exam.setPaperId(req.getPaperId());
        exam.setStartTime(req.getStartTime());
        exam.setEndTime(req.getEndTime());
        examRepository.save(exam);
        return new ExamView(exam.getId(), exam.getName(), exam.getCourseId(), exam.getPaperId(), exam.getStartTime(), exam.getEndTime());
    }

    public List<ExamView> listForStudent() {
        LocalDateTime now = LocalDateTime.now();
        return examRepository.findAll().stream()
                .filter(e -> !now.isBefore(e.getStartTime()) && !now.isAfter(e.getEndTime()))
                .map(e -> new ExamView(e.getId(), e.getName(), e.getCourseId(), e.getPaperId(), e.getStartTime(), e.getEndTime()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ExamSubmitResponse submit(Long studentId, Long examId, List<ExamAnswerRequest> answers) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new BizException(404, "exam not found"));
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(exam.getStartTime())) {
            throw new BizException(400, "exam not started");
        }
        if (now.isAfter(exam.getEndTime())) {
            throw new BizException(400, "exam ended");
        }

        Optional<ExamRecord> existingRecord = recordRepository.findByExamIdAndStudentId(examId, studentId);
        if (existingRecord.isPresent() && existingRecord.get().getStatus() == ExamRecordStatus.SUBMITTED) {
            throw new BizException(400, "already submitted");
        }

        ExamRecord record = existingRecord.orElseGet(() -> {
            ExamRecord r = new ExamRecord();
            r.setExamId(examId);
            r.setStudentId(studentId);
            r.setStartedAt(now);
            r.setStatus(ExamRecordStatus.IN_PROGRESS);
            return recordRepository.save(r);
        });

        answerRepository.deleteByRecordId(record.getId());
        for (ExamAnswerRequest a : answers) {
            ExamAnswer answer = new ExamAnswer();
            answer.setRecordId(record.getId());
            answer.setQuestionId(a.getQuestionId());
            answer.setAnswerContent(a.getAnswerContent());
            answerRepository.save(answer);
        }

        int total = calculateScore(exam.getPaperId(), record.getId());

        record.setStatus(ExamRecordStatus.SUBMITTED);
        record.setSubmittedAt(now);
        recordRepository.save(record);

        ExamResult result = resultRepository.findByRecordId(record.getId()).orElse(new ExamResult());
        result.setRecordId(record.getId());
        result.setTotalScore(total);
        result.setSubmittedAt(now);
        resultRepository.save(result);

        return new ExamSubmitResponse(record.getId(), total);
    }

    private int calculateScore(Long paperId, Long recordId) {
        List<PaperQuestion> paperQuestions = paperQuestionRepository.findByPaperIdOrderBySortOrderAsc(paperId);
        if (paperQuestions.isEmpty()) {
            return 0;
        }

        List<Long> qIds = paperQuestions.stream().map(PaperQuestion::getQuestionId).collect(Collectors.toList());
        Map<Long, Question> questionMap = questionRepository.findAllById(qIds).stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
        Map<Long, String> answerMap = answerRepository.findByRecordId(recordId).stream()
                .collect(Collectors.toMap(ExamAnswer::getQuestionId, a -> QuestionService.normalizeAnswer("MULTIPLE", a.getAnswerContent()), (a, b) -> b));

        int total = 0;
        for (PaperQuestion pq : paperQuestions) {
            Question q = questionMap.get(pq.getQuestionId());
            if (q == null) {
                continue;
            }
            String userAnswer = QuestionService.normalizeAnswer(q.getType().name(), answerMap.getOrDefault(q.getId(), ""));
            String correct = QuestionService.normalizeAnswer(q.getType().name(), q.getAnswer());
            if (correct.equals(userAnswer)) {
                total += pq.getScore();
            }
        }
        return total;
    }

    public List<Map<String, Object>> studentResults(Long studentId) {
        List<ExamRecord> records = recordRepository.findByStudentId(studentId);
        if (records.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, Exam> examMap = examRepository.findAllById(records.stream().map(ExamRecord::getExamId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Exam::getId, Function.identity()));

        List<Long> recordIds = records.stream().map(ExamRecord::getId).collect(Collectors.toList());
        Map<Long, ExamResult> resultMap = resultRepository.findByRecordIdIn(recordIds).stream()
                .collect(Collectors.toMap(ExamResult::getRecordId, Function.identity()));

        List<Map<String, Object>> result = new ArrayList<>();
        for (ExamRecord r : records) {
            ExamResult er = resultMap.get(r.getId());
            if (er == null) {
                continue;
            }
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("recordId", r.getId());
            item.put("examId", r.getExamId());
            item.put("examName", examMap.get(r.getExamId()) != null ? examMap.get(r.getExamId()).getName() : "");
            item.put("totalScore", er.getTotalScore());
            item.put("submittedAt", er.getSubmittedAt());
            result.add(item);
        }
        return result;
    }

    public List<Map<String, Object>> teacherResults(Long examId) {
        List<ExamRecord> records = recordRepository.findByExamIdAndStatus(examId, ExamRecordStatus.SUBMITTED);
        if (records.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> recordIds = records.stream().map(ExamRecord::getId).collect(Collectors.toList());
        Map<Long, ExamResult> resultMap = resultRepository.findByRecordIdIn(recordIds).stream()
                .collect(Collectors.toMap(ExamResult::getRecordId, Function.identity()));
        Map<Long, SysUser> userMap = userRepository.findAllById(records.stream().map(ExamRecord::getStudentId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(SysUser::getId, Function.identity()));

        List<Map<String, Object>> out = new ArrayList<>();
        for (ExamRecord r : records) {
            ExamResult result = resultMap.get(r.getId());
            if (result == null) {
                continue;
            }
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("recordId", r.getId());
            item.put("studentId", r.getStudentId());
            item.put("studentUsername", userMap.get(r.getStudentId()) != null ? userMap.get(r.getStudentId()).getUsername() : "");
            item.put("totalScore", result.getTotalScore());
            item.put("submittedAt", result.getSubmittedAt());
            out.add(item);
        }
        return out;
    }
}
