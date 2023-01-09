package com.example.courseservice.dto.request.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TuitionStatus {
    private Long idStudent;
    private int tuitionStatus;
}
