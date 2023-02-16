package com.example.courseservice.service.implement;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.response.course.StudentReponse;
import com.example.courseservice.dto.response.course.TeacherResponse;
import com.example.courseservice.feignclients.StudentFeignClient;
import com.example.courseservice.feignclients.TeacherFeignClient;
import com.example.courseservice.service.CommonService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    private TeacherFeignClient teacherFeignClient;

    @CircuitBreaker(name="studentService", fallbackMethod = "fallbackStudentDetail")
    public StudentReponse detailStudent(Long id) {
        return studentFeignClient.detailStudent(id);
    }
    public StudentReponse fallbackStudentDetail (Long id, Throwable th){
        return new StudentReponse();
    }

    @CircuitBreaker(name="teacherService", fallbackMethod = "fallbackTeacherDetail")
    @Override
    public TeacherResponse detailTeacher(Long id) {
        return teacherFeignClient.detailTeacher(id).getResponse();
    }
    public TeacherResponse fallbackTeacherDetail (Long id, Throwable th){
        return new TeacherResponse();
    }

    @CircuitBreaker(name="teacherService", fallbackMethod = "fallbackListTeachers")
    @Override
    public List<TeacherResponse> listTeachers(List<Long> listId) {
        return teacherFeignClient.listTeachers(listId).getResponse();
    }
    public List<TeacherResponse> fallbackListTeachers (List<Long> listId, Throwable th){
        return new ArrayList<TeacherResponse>();
    }

    @CircuitBreaker(name="studentService", fallbackMethod = "fallbackListStudents")
    @Override
    public List<StudentReponse> listStudents(List<Long> listId) {
        return studentFeignClient.listStudents(listId).getResponse();
    }
    public List<StudentReponse> fallbackListStudents (List<Long> listId, Throwable th){
        return new ArrayList<StudentReponse>();
    }
}
