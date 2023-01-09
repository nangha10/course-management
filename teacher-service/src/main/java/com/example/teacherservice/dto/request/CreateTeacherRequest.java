package com.example.teacherservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeacherRequest {
    private Long id;
    private String name;
    private Date dob;
    private String email;
    private String phone;
    private String experience;
}
