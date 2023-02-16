package com.example.courseservice.service;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.request.course.CourseUpdateRequest;
import com.example.courseservice.dto.request.course.TuitionStatus;
import com.example.courseservice.dto.response.course.CourseDetailResponse;
import com.example.courseservice.dto.response.course.CourseReponse;
import com.example.courseservice.dto.response.course.StudentInf;
import com.example.courseservice.dto.response.course.StudentReponse;

import java.util.List;

public interface CourseService {
    ServiceResponse<CourseDetailResponse> courseDetail(Long id);
    ServiceResponse<CourseReponse> create(CourseCreateRequest courseCreateRequest);
    ServiceResponse<CourseReponse> update(Long id, CourseCreateRequest courseCreateRequest);
    ServiceResponse<List<CourseReponse>> allCourse();
    ServiceResponse<List<StudentInf>> listStudents(Long courseId);
    ServiceResponse<CourseReponse> delete(Long id);
    ServiceResponse<Boolean> addStudentsToCourse(Long courseId, List<Long> studentsId);
    ServiceResponse<Boolean> removeStudentsFromCourse(Long courseId, List<Long> studentsId);
    ServiceResponse<Boolean> updateTuitionStatus(Long courseId, List<TuitionStatus> listTuitionStatus);
}
