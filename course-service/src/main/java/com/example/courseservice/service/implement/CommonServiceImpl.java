package com.example.courseservice.service.implement;

import com.example.courseservice.dto.response.course.StudentReponse;
import com.example.courseservice.dto.response.course.TeacherResponse;
import com.example.courseservice.feignclients.StudentFeignClient;
import com.example.courseservice.feignclients.TeacherFeignClient;
import com.example.courseservice.service.CommonService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    private TeacherFeignClient teacherFeignClient;

    @CircuitBreaker(name="studentService", fallbackMethod = "fallbackStudentDetail")
    public StudentReponse detailStudent(Long id) {
        StudentReponse studentResponse = studentFeignClient.detailStudent(id);

        return studentResponse;
    }
    public StudentReponse fallbackStudentDetail (Long id, Throwable th){
        return new StudentReponse();
    }

    @CircuitBreaker(name="teacherService", fallbackMethod = "fallbackTeacherDetail")
    @Override
    public TeacherResponse detailTeacher(Long id) {
        TeacherResponse teacherResponse = teacherFeignClient.detailTeacher(id);

        return teacherResponse;
    }
    public TeacherResponse fallbackTeacherDetail (Long id, Throwable th){
        return new TeacherResponse();
    }
}
