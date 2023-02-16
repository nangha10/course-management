package com.example.teacherservice.controller;

import com.example.teacherservice.dto.ServiceResponse;
import com.example.teacherservice.dto.request.CreateTeacherRequest;
import com.example.teacherservice.dto.response.TeacherResponse;
import com.example.teacherservice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teachers")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping
    public ServiceResponse<TeacherResponse> createTeacher(@RequestBody CreateTeacherRequest createTeacherRequest) {
        return teacherService.create(createTeacherRequest);
    }

    @GetMapping
    public ServiceResponse<List<TeacherResponse>> all() {

        return teacherService.listTeachers();
    }

    @PutMapping("")
    public ServiceResponse<TeacherResponse> updateTeacher(@RequestBody CreateTeacherRequest createTeacherRequest) {
        return teacherService.update(createTeacherRequest);
    }

    @GetMapping("/{id}")
    public ServiceResponse<TeacherResponse> detailTeacher(@PathVariable Long id) {
        return teacherService.detail(id);
    }

    @PostMapping("/")
    public ServiceResponse<List<TeacherResponse>> listTeachers(@RequestBody List<Long> listId) {
        return teacherService.findListTeachers(listId);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<TeacherResponse> deleteTeacher(@PathVariable Long id) {
        return teacherService.delete(id);
    }
}
