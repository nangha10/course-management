package com.example.courseservice.dto.response.lesson;

import com.example.courseservice.entity.Lesson;
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
    private Long teacherId;
    private Date date;

    public LessonReponse (Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.date = lesson.getDate();
        this.teacherId = lesson.getTeacherId();
    }
}
