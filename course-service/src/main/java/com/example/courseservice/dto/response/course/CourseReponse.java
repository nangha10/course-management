package com.example.courseservice.dto.response.course;

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
}
