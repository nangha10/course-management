package com.example.courseservice.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailResponse {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Long tuition;
    private List<TeacherInf> listTeachers;
}
