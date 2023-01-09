package com.example.courseservice.controller;

import com.example.courseservice.dto.request.lesson.LessonCreateRequest;
import com.example.courseservice.dto.request.lesson.RollCallStatus;
import com.example.courseservice.dto.response.lesson.AllLessonResponse;
import com.example.courseservice.dto.response.lesson.LessonDetailResponse;
import com.example.courseservice.dto.response.lesson.RollCallResponse;
import com.example.courseservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lesson")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @PostMapping("/create")
    public String createStudent(@RequestBody LessonCreateRequest lessonCreateRequest) {
        return lessonService.create(lessonCreateRequest);
    }

    @GetMapping("/all/{courseId}")
    public AllLessonResponse all(@PathVariable Long courseId) {
        return lessonService.allLesson(courseId);
    }

    @PutMapping("/update")
    public String updateStudent(@RequestBody LessonCreateRequest lessonCreateRequest) {
        return lessonService.update(lessonCreateRequest);
    }

    @GetMapping("/detail/{id}")
    public LessonDetailResponse lessonDetail(@PathVariable Long id) {

        return lessonService.lessonDetail(id);
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {

        return lessonService.delete(id);
    }

    @PostMapping("/roll-call/{lessonId}")
    public String rollCall(@PathVariable Long lessonId,@RequestBody List<RollCallStatus> studentId) {
        return lessonService.rollCall(lessonId, studentId);
    }

    @GetMapping("/view-roll-call/{lessonId}")
    public RollCallResponse rollCall(@PathVariable Long lessonId) {

        return lessonService.viewRollCall(lessonId);
    }
}
