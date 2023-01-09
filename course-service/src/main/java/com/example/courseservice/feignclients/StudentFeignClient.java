package com.example.courseservice.feignclients;

import com.example.courseservice.dto.response.course.StudentReponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "api-gateway")
public interface StudentFeignClient {
    @PostMapping("/student-service/api/student/detail/{id}")
    public StudentReponse detailStudent(@PathVariable Long id);
}
