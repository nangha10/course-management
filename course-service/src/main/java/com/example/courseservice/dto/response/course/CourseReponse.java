package com.example.courseservice.dto.response.course;

import com.example.courseservice.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseReponse {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;

    public CourseReponse (Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
    }
}
