package com.example.courseservice.feignclients;

import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.response.course.TeacherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "api-gateway")
public interface TeacherFeignClient {
    @GetMapping("/teacher-service/api/v1/teachers/{id}")
    ServiceResponse<TeacherResponse> detailTeacher(@PathVariable Long id);

    @PostMapping("/teacher-service/api/v1/teachers/")
    ServiceResponse<List<TeacherResponse>> listTeachers(@RequestBody List<Long> listId);
}
