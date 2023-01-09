package com.example.courseservice.dto.response.lesson;

import com.example.courseservice.dto.response.course.StudentInf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollCallResponse {
    private Long lessonId;
    private Date date;
    private List<RollCallInf> rollCallInf;
}
