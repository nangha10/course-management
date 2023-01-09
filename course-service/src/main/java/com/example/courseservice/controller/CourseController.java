package com.example.courseservice.controller;

import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.request.course.TuitionStatus;
import com.example.courseservice.dto.response.course.*;
import com.example.courseservice.service.CommonService;
import com.example.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course")
public class CourseController {
   @Autowired
   CourseService courseService;

    @Autowired
    CommonService commonService;

    @PostMapping("/create")
    public String createStudent(@RequestBody CourseCreateRequest courseCreateRequest) {
        return courseService.create(courseCreateRequest);
    }

    @GetMapping("/all")
    public  List<CourseReponse> all() {
        return courseService.allCourse();
    }

    @PutMapping("/update")
    public String updateStudent(@RequestBody CourseCreateRequest courseCreateRequest) {
        return courseService.update(courseCreateRequest);
    }

    @GetMapping("/detail/{id}")
    public CourseDetailResponse courseDetail(@PathVariable Long id) {
        return courseService.courseDetail(id);
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {

        return courseService.delete(id);
    }

    @GetMapping("/listStudents/{courseId}")
    public List<StudentInf> listStudents(@PathVariable Long courseId) {

        return courseService.listStudents(courseId);
    }

    @PostMapping("/add-students/{courseId}")
    public String addStudentsToCourse(@PathVariable Long courseId, @RequestBody List<Long> studentsId) {

        return courseService.addStudentsToCourse(courseId, studentsId);
    }

    @PostMapping("/remove-students/{courseId}")
    public String removeStudentsFromCourse(@PathVariable Long courseId, @RequestBody List<Long> studentsId) {

        return courseService.removeStudentsFromCourse(courseId, studentsId);
    }

    @PutMapping("/update-tuition-status/{courseId}")
    public String updateTuitionStatus(@PathVariable Long courseId, @RequestBody List<TuitionStatus> listTuitionStatus) {

        return courseService.updateTuitionStatus(courseId, listTuitionStatus);
    }

    @GetMapping("/detail-teacher/{id}")
    public TeacherResponse teacherDetail(@PathVariable Long id) {
        return commonService.detailTeacher(id);
    }


}
