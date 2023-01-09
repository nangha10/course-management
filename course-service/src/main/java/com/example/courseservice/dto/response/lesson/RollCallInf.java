package com.example.courseservice.dto.response.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollCallInf {
    private Long studentId;
    private String studentName;
    private Date dob;
    private String email;
    private int status;
}
