package com.example.studentservice.dto.response;

import com.example.studentservice.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private Date dob;
    private String phone;
    private int grade;
    private String school;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.dob = student.getDob();
        this.phone = student.getPhone();
        this.grade = student.getGrade();
        this.school = student.getSchool();
    }
}
