package com.example.examsystem.service;

import com.example.examsystem.common.BizException;
import com.example.examsystem.dto.CourseRequest;
import com.example.examsystem.entity.Course;
import com.example.examsystem.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Course create(CourseRequest req) {
        Course c = new Course();
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        return courseRepository.save(c);
    }

    public Course update(Long id, CourseRequest req) {
        Course c = courseRepository.findById(id).orElseThrow(() -> new BizException(404, "course not found"));
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        return courseRepository.save(c);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
