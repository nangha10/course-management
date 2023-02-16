package com.example.courseservice.service;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.response.course.StudentReponse;
import com.example.courseservice.dto.response.course.TeacherResponse;

import java.util.List;

public interface CommonService {
    StudentReponse detailStudent(Long id);
    List<StudentReponse> listStudents(List<Long> listId);

    TeacherResponse detailTeacher(Long id);
    List<TeacherResponse> listTeachers(List<Long> listId);
}
