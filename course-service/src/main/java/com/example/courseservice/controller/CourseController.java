package com.example.courseservice.controller;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.request.course.TuitionStatus;
import com.example.courseservice.dto.response.course.*;
import com.example.courseservice.dto.response.lesson.AllLessonResponse;
import com.example.courseservice.service.CommonService;
import com.example.courseservice.service.CourseService;
import com.example.courseservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    CommonService commonService;

    @Autowired
    LessonService lessonService;

    @PostMapping("")
    public ServiceResponse<CourseReponse> createCourse(@RequestBody CourseCreateRequest courseCreateRequest) {
        return courseService.create(courseCreateRequest);
    }

    @GetMapping("")
    public  ServiceResponse<List<CourseReponse>> allCourse() {
        return courseService.allCourse();
    }

    @PutMapping("/{id}")
    public ServiceResponse<CourseReponse> updateCourse(@PathVariable Long id, @RequestBody CourseCreateRequest courseCreateRequest) {
        return courseService.update(id, courseCreateRequest);
    }

    @GetMapping("/{id}")
    public ServiceResponse<CourseDetailResponse> courseDetail(@PathVariable Long id) {
        return courseService.courseDetail(id);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<CourseReponse> deleteCourse(@PathVariable Long id) {

        return courseService.delete(id);
    }

    @GetMapping("/{courseId}/students")
    public ServiceResponse<List<StudentInf>> listStudents(@PathVariable Long courseId) {

        return courseService.listStudents(courseId);
    }

    @PostMapping("/{courseId}/students")
    public ServiceResponse<Boolean> addStudentsToCourse(@PathVariable Long courseId, @RequestBody List<Long> studentsId) {

        return courseService.addStudentsToCourse(courseId, studentsId);
    }

    @DeleteMapping("/{courseId}/students")
    public ServiceResponse<Boolean> removeStudentsFromCourse(@PathVariable Long courseId, @RequestBody List<Long> studentsId) {
        return courseService.removeStudentsFromCourse(courseId, studentsId);
    }

    @PutMapping("/{courseId}/tuition")
    public ServiceResponse<Boolean> updateTuitionStatus(@PathVariable Long courseId, @RequestBody List<TuitionStatus> listTuitionStatus) {

        return courseService.updateTuitionStatus(courseId, listTuitionStatus);
    }

    @GetMapping("/{id}/teacher")
    public TeacherResponse teacherDetail(@PathVariable Long id) {
        return commonService.detailTeacher(id);
    }

    @GetMapping("/{courseId}/lessons")
    public ServiceResponse<AllLessonResponse> all(@PathVariable Long courseId) {
        return lessonService.allLesson(courseId);
    }
}
