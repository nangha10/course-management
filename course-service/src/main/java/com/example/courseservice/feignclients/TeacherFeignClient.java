package com.example.courseservice.feignclients;

import com.example.courseservice.dto.response.course.TeacherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "api-gateway")
public interface TeacherFeignClient {
    @GetMapping("/teacher-service/api/teacher/detail/{id}")
    public TeacherResponse detailTeacher(@PathVariable Long id);
}
