package com.example.courseservice.dto.response.lesson;

import com.example.courseservice.dto.response.course.CourseInf;
import com.example.courseservice.dto.response.course.TeacherInf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDetailResponse {
    private Long id;
    private String title;
    private CourseInf courseInf;
    private TeacherInf teacherInf;
    private Date date;
    private String description;
}
