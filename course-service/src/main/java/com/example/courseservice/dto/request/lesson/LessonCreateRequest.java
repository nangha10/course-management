package com.example.courseservice.dto.request.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonCreateRequest {
    private Long id;
    private String title;
    private Long courseId;
    private Long teacherId;
    private Date date;
    private String description;
}
