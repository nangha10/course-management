package com.example.teacherservice.service;

import com.example.teacherservice.dto.ServiceResponse;
import com.example.teacherservice.dto.request.CreateTeacherRequest;
import com.example.teacherservice.dto.response.TeacherResponse;

import java.util.List;

public interface TeacherService {

    ServiceResponse<TeacherResponse> create(CreateTeacherRequest createTeacherRequest);

    ServiceResponse<TeacherResponse> update(CreateTeacherRequest updateTeacherRequest);

    ServiceResponse<TeacherResponse> detail(Long id);

    ServiceResponse<TeacherResponse> delete(Long id);

    ServiceResponse<List<TeacherResponse>> listTeachers();

    ServiceResponse<List<TeacherResponse>> findListTeachers(List<Long> listId);

}
