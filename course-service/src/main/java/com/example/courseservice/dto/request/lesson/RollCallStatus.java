package com.example.courseservice.dto.request.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollCallStatus {
    private Long idStudent;
    private int status;
}
