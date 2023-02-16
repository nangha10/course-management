package com.example.teacherservice.dto.response;

import com.example.teacherservice.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {
    private Long id;
    private String name;
    private String email;
    private Date dob;
    private String phone;
    private String experience;

    public TeacherResponse(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.dob = teacher.getDob();
        this.phone = teacher.getPhone();
        this.experience = teacher.getExperience();
    }
}
