package com.example.courseservice.service;

import com.example.courseservice.dto.request.lesson.LessonCreateRequest;
import com.example.courseservice.dto.request.lesson.LessonUpdateRequest;
import com.example.courseservice.dto.request.lesson.RollCallStatus;
import com.example.courseservice.dto.response.lesson.AllLessonResponse;
import com.example.courseservice.dto.response.lesson.LessonDetailResponse;
import com.example.courseservice.dto.response.lesson.LessonReponse;
import com.example.courseservice.dto.response.lesson.RollCallResponse;

import java.util.List;

public interface LessonService {
    String create(LessonCreateRequest lessonCreateRequest);
    String update(LessonCreateRequest lessonCreateRequest);
    LessonDetailResponse lessonDetail(Long id);
    AllLessonResponse allLesson(Long courseId);
    String delete(Long id);
    String rollCall(Long lessonId, List<RollCallStatus> studentId);
    RollCallResponse viewRollCall(Long lessonId);
}
