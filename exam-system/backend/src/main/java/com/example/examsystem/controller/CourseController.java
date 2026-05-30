package com.example.examsystem.controller;

import com.example.examsystem.common.ApiResponse;
import com.example.examsystem.dto.CourseRequest;
import com.example.examsystem.entity.Course;
import com.example.examsystem.service.CourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public ApiResponse<List<Course>> list() {
        return ApiResponse.ok(courseService.list());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ApiResponse<Course> create(@Validated @RequestBody CourseRequest req) {
        return ApiResponse.ok(courseService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ApiResponse<Course> update(@PathVariable Long id, @Validated @RequestBody CourseRequest req) {
        return ApiResponse.ok(courseService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ApiResponse.ok(null);
    }
}

