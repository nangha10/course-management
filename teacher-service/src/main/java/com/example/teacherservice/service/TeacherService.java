package com.example.teacherservice.service;

import com.example.teacherservice.dto.request.CreateTeacherRequest;
import com.example.teacherservice.dto.response.TeacherResponse;

import java.util.List;

public interface TeacherService {

    String create(CreateTeacherRequest createTeacherRequest);

    String update(CreateTeacherRequest updateTeacherRequest);

    TeacherResponse detail(Long id);

    String delete(Long id);

    List<TeacherResponse> listTeachers();

}
