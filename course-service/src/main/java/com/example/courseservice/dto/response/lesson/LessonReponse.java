package com.example.courseservice.dto.response.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonReponse {
    private Long id;
    private String title;
    private String teacherName;
    private Date date;
}
