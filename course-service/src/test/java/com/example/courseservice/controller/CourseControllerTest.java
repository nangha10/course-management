package com.example.courseservice.controller;

import com.example.courseservice.dto.MessageCode;
import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.response.course.CourseReponse;
import com.example.courseservice.entity.Course;
import com.example.courseservice.service.CourseService;
import com.example.courseservice.service.implement.CourseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ObjectMapper objectMapper;

    @Mock
    CourseService courseService;

    @InjectMocks
    CourseController courseController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void createCourseTest() throws Exception {

        CourseCreateRequest courseCreateRequest = new CourseCreateRequest();
        courseCreateRequest.setId(1L);
        List<Long> listTeacherId = new ArrayList<>();
        listTeacherId.add(2L);
        courseCreateRequest.setListTeacherId(listTeacherId);

        Course course = new Course();
        course.setId(1L);
        CourseReponse courseReponse = new CourseReponse(course);

//        when(courseService.create(courseCreateRequest)).thenReturn(new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(courseCreateRequest)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void updateCourseTest() throws Exception {
        Long id = 1L;
        CourseCreateRequest courseCreateRequest = new CourseCreateRequest();

        List<Long> listTeacherId = new ArrayList<>();
        listTeacherId.add(2L);
        courseCreateRequest.setListTeacherId(listTeacherId);

        Course course = new Course();
        course.setId(1L);
        CourseReponse courseReponse = new CourseReponse(course);
        when(courseService.update(id, courseCreateRequest)).thenReturn(new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/courses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(courseCreateRequest)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void allCourseTest() throws Exception {
        Course course1 = new Course(1L, "c1", new Date(), new Date(), 10L, 0, new Date(), new Date());
        Course course2 = new Course(2L, "c2", new Date(), new Date(), 10L, 0, new Date(), new Date());

        List<CourseReponse> courseResponseList = new ArrayList<>();
        courseResponseList.add(new CourseReponse(course1));
        courseResponseList.add(new CourseReponse(course2));

        when(courseService.allCourse()).thenReturn(new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseResponseList));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void detailCourseTest() throws Exception {
        Long id = 1L;
        Course course = new Course(1L, "c1", new Date(), new Date(), 10L, 0, new Date(), new Date());
        CourseReponse courseReponse = new CourseReponse(course);

        when(courseService.allCourse()).thenReturn(new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

    }

}