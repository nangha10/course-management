package com.example.studentservice.service;

import com.example.studentservice.dto.ServiceResponse;
import com.example.studentservice.dto.request.CreateStudentRequest;
import com.example.studentservice.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {

    ServiceResponse<StudentResponse> create(CreateStudentRequest createStudentRequest);

    ServiceResponse<StudentResponse> update(CreateStudentRequest updateStudentRequest);

    ServiceResponse<StudentResponse> detail(Long id);

    ServiceResponse<StudentResponse> delete(Long id);

    ServiceResponse<List<StudentResponse>> listStudents();

    ServiceResponse<List<StudentResponse>> findListStudents(List<Long> listId);

}
