package com.example.courseservice.controller;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.lesson.LessonCreateRequest;
import com.example.courseservice.dto.request.lesson.RollCallStatus;
import com.example.courseservice.dto.response.lesson.LessonDetailResponse;
import com.example.courseservice.dto.response.lesson.LessonReponse;
import com.example.courseservice.dto.response.lesson.RollCallResponse;
import com.example.courseservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/lessons")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @PostMapping("")
    public ServiceResponse<LessonReponse> createStudent(@RequestBody LessonCreateRequest lessonCreateRequest) {
        return lessonService.create(lessonCreateRequest);
    }

    @PutMapping("")
    public ServiceResponse<LessonReponse> updateStudent(@RequestBody LessonCreateRequest lessonCreateRequest) {
        return lessonService.update(lessonCreateRequest);
    }

    @GetMapping("/{id}")
    public ServiceResponse<LessonDetailResponse> lessonDetail(@PathVariable Long id) {

        return lessonService.lessonDetail(id);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<LessonReponse> deleteCourse(@PathVariable Long id) {

        return lessonService.delete(id);
    }

    @PostMapping("/{lessonId}/roll-call")
    public ServiceResponse<Boolean> rollCall(@PathVariable Long lessonId, @RequestBody List<RollCallStatus> studentId) {
        return lessonService.rollCall(lessonId, studentId);
    }

    @GetMapping("/{lessonId}/roll-call")
    public ServiceResponse<RollCallResponse> rollCall(@PathVariable Long lessonId) {
        return lessonService.viewRollCall(lessonId);
    }
}
