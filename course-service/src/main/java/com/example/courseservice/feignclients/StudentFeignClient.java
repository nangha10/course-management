package com.example.courseservice.feignclients;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.response.course.StudentReponse;
import com.example.courseservice.dto.response.course.TeacherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "api-gateway")
public interface StudentFeignClient {
    @GetMapping("/student-service/api/v1/students/{id}")
    public StudentReponse detailStudent(@PathVariable Long id);

    @PostMapping("/teacher-service/api/v1/students/")
    ServiceResponse<List<StudentReponse>> listStudents(@RequestBody List<Long> listId);
}
