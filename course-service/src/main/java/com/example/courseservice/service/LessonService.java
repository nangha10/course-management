package com.example.courseservice.service;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.lesson.LessonCreateRequest;
import com.example.courseservice.dto.request.lesson.LessonUpdateRequest;
import com.example.courseservice.dto.request.lesson.RollCallStatus;
import com.example.courseservice.dto.response.lesson.AllLessonResponse;
import com.example.courseservice.dto.response.lesson.LessonDetailResponse;
import com.example.courseservice.dto.response.lesson.LessonReponse;
import com.example.courseservice.dto.response.lesson.RollCallResponse;

import java.util.List;

public interface LessonService {
    ServiceResponse<LessonReponse> create(LessonCreateRequest lessonCreateRequest);
    ServiceResponse<LessonReponse> update(LessonCreateRequest lessonCreateRequest);
    ServiceResponse<LessonDetailResponse> lessonDetail(Long id);
    ServiceResponse<AllLessonResponse> allLesson(Long courseId);
    ServiceResponse<LessonReponse> delete(Long id);
    ServiceResponse<Boolean> rollCall(Long lessonId, List<RollCallStatus> studentId);
    ServiceResponse<RollCallResponse> viewRollCall(Long lessonId);
}
