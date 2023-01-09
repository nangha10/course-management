package com.example.courseservice.dto.response.lesson;

import com.example.courseservice.dto.response.course.CourseInf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllLessonResponse {
    private CourseInf courseInf;
    private List<LessonReponse> listLesson;
}
