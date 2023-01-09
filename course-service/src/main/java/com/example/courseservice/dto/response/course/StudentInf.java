package com.example.courseservice.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInf {
    private Long id;
    private String name;
    private String email;
    private Date dob;
    private int tuitionStatus;
}
