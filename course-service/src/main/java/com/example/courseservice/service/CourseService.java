package com.example.courseservice.service;

import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.request.course.CourseUpdateRequest;
import com.example.courseservice.dto.request.course.TuitionStatus;
import com.example.courseservice.dto.response.course.CourseDetailResponse;
import com.example.courseservice.dto.response.course.CourseReponse;
import com.example.courseservice.dto.response.course.StudentInf;
import com.example.courseservice.dto.response.course.StudentReponse;

import java.util.List;

public interface CourseService {
    String create(CourseCreateRequest courseCreateRequest);
    String update(CourseCreateRequest courseCreateRequest);
    CourseDetailResponse courseDetail(Long id);
    List<CourseReponse> allCourse();
    List<StudentInf> listStudents(Long courseId);
    String delete(Long id);
    String addStudentsToCourse(Long courseId, List<Long> studentsId);
    String removeStudentsFromCourse(Long courseId, List<Long> studentsId);
    String updateTuitionStatus(Long courseId, List<TuitionStatus> listTuitionStatus);
}
