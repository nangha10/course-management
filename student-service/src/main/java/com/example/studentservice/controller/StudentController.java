package com.example.studentservice.controller;

import com.example.studentservice.dto.ServiceResponse;
import com.example.studentservice.dto.request.CreateStudentRequest;
import com.example.studentservice.dto.response.StudentResponse;
import com.example.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping
    public ServiceResponse<StudentResponse> createStudent(@RequestBody CreateStudentRequest createStudentRequest) {
        return studentService.create(createStudentRequest);
    }

    @GetMapping
    public ServiceResponse<List<StudentResponse>> all() {
        return studentService.listStudents();
    }

    @PutMapping("")
    public ServiceResponse<StudentResponse> updateStudent(@RequestBody CreateStudentRequest createStudentRequest) {
        return studentService.update(createStudentRequest);
    }

    @GetMapping("/{id}")
    public ServiceResponse<StudentResponse> detailStudent(@PathVariable Long id) {

        return studentService.detail(id);
    }

    @PostMapping("/")
    public ServiceResponse<List<StudentResponse>> listTeachers(@RequestBody List<Long> listId) {
        return studentService.findListStudents(listId);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<StudentResponse> deleteStudent(@PathVariable Long id) {

        return studentService.delete(id);
    }
}
