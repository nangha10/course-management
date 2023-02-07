package com.example.studentservice.controller;

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
    public String createStudent(@RequestBody CreateStudentRequest createStudentRequest) {
        return studentService.create(createStudentRequest);
    }

    @GetMapping
    public List<StudentResponse> all() {
        return studentService.listStudents();
    }

    @PutMapping("")
    public String updateStudent(@RequestBody CreateStudentRequest createStudentRequest) {
        return studentService.update(createStudentRequest);
    }

    @GetMapping("/{id}")
    public StudentResponse detailStudent(@PathVariable Long id) {

        return studentService.detail(id);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {

        return studentService.delete(id);
    }
}
