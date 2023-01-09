package com.example.courseservice.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentReponse {
    private Long id;
    private String name;
    private String email;
    private Date dob;
    private String phone;
    private int grade;
    private String school;
    private Date createdDate;
    private Date updatedDate;
}
