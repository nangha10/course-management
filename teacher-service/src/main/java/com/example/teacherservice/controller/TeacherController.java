package com.example.teacherservice.controller;

import com.example.teacherservice.dto.request.CreateTeacherRequest;
import com.example.teacherservice.dto.response.TeacherResponse;
import com.example.teacherservice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/create")
    public String createTeacher(@RequestBody CreateTeacherRequest createTeacherRequest) {
        return teacherService.create(createTeacherRequest);
    }

    @GetMapping("/all")
    public List<TeacherResponse> all() {
        return teacherService.listTeachers();
    }

    @PutMapping("/update")
    public String updateTeacher(@RequestBody CreateTeacherRequest createTeacherRequest) {
        return teacherService.update(createTeacherRequest);
    }

    @GetMapping("/detail/{id}")
    public TeacherResponse detailTeacher(@PathVariable Long id) {

        return teacherService.detail(id);
    }

    @PostMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {

        return teacherService.delete(id);
    }
}
