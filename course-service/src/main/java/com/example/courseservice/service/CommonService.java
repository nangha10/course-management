package com.example.courseservice.service;

import com.example.courseservice.dto.response.course.StudentReponse;
import com.example.courseservice.dto.response.course.TeacherResponse;

public interface CommonService {
    StudentReponse detailStudent(Long id);
    TeacherResponse detailTeacher(Long id);
}
