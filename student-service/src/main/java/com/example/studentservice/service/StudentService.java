package com.example.studentservice.service;

import com.example.studentservice.dto.request.CreateStudentRequest;
import com.example.studentservice.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {

    String create(CreateStudentRequest createStudentRequest);

    String update(CreateStudentRequest updateStudentRequest);

    StudentResponse detail(Long id);

    String delete(Long id);

    List<StudentResponse> listStudents();

}
