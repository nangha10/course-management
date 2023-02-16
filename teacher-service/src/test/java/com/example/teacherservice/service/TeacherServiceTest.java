package com.example.teacherservice.service;

import com.example.teacherservice.dto.MessageCode;
import com.example.teacherservice.dto.ServiceResponse;
import com.example.teacherservice.dto.request.CreateTeacherRequest;
import com.example.teacherservice.dto.response.TeacherResponse;
import com.example.teacherservice.entity.Teacher;
import com.example.teacherservice.repository.TeacherRepository;
import com.example.teacherservice.service.implement.TeacherServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    TeacherRepository teacherRepository;

    @InjectMocks
    TeacherServiceImpl teacherServiceImp;

    @Nested
    class createTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            CreateTeacherRequest createTeacherRequest = new CreateTeacherRequest();

            Teacher teacher = new Teacher();
            teacher.setId(1L);

            TeacherResponse teacherResponse = new TeacherResponse(teacher);

            when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

            ServiceResponse<TeacherResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, teacherResponse);

            ServiceResponse<TeacherResponse> actual = teacherServiceImp.create(createTeacherRequest);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class updateTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            CreateTeacherRequest createTeacherRequest = new CreateTeacherRequest();
            createTeacherRequest.setId(1L);
            Teacher teacher = new Teacher();
            teacher.setId(1L);

            TeacherResponse teacherResponse = new TeacherResponse(teacher);

            when(teacherRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(teacher));
            when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

            ServiceResponse<TeacherResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, teacherResponse);

            ServiceResponse<TeacherResponse> actual = teacherServiceImp.update(createTeacherRequest);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnSuccess_WhenTeacherNotExist() {
            CreateTeacherRequest createTeacherRequest = new CreateTeacherRequest();
            createTeacherRequest.setId(1L);

            when(teacherRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());
            ServiceResponse<TeacherResponse> expected = new ServiceResponse(MessageCode.TEACHER_NOT_EXISTED, MessageCode.TEACHER_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<TeacherResponse> actual = teacherServiceImp.update(createTeacherRequest);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class detailTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            Long id = 1L;
            Teacher teacher = new Teacher();
            teacher.setId(1L);

            TeacherResponse teacherResponse = new TeacherResponse(teacher);

            when(teacherRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(teacher));

            ServiceResponse<TeacherResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, teacherResponse);

            ServiceResponse<TeacherResponse> actual = teacherServiceImp.detail(id);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnSuccess_WhenTeacherNotExist() {
            Long id = 1L;
            when(teacherRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());
            ServiceResponse<TeacherResponse> expected = new ServiceResponse(MessageCode.TEACHER_NOT_EXISTED, MessageCode.TEACHER_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<TeacherResponse> actual = teacherServiceImp.detail(id);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class deleteTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long id = 1L;
            Teacher teacher = new Teacher(1L, "c1", "", new Date(), "", "", 0, new Date(), new Date());
            //when

            when(teacherRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(teacher));

            ServiceResponse<TeacherResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, new TeacherResponse(teacher));

            ServiceResponse<TeacherResponse> actual = teacherServiceImp.delete(id);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnError_WhenStudentNotExist() {
            //given
            Long id = 1L;
            //when
            when(teacherRepository.findByIdAndIsDeleted(id, 0)).thenReturn(Optional.empty());

            ServiceResponse<TeacherResponse> expected = new ServiceResponse(MessageCode.TEACHER_NOT_EXISTED, MessageCode.TEACHER_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<TeacherResponse> actual = teacherServiceImp.delete(id);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class listTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            List<Teacher> listTeachers = new ArrayList<>();
            Teacher teacher = new Teacher(1L, "c1", "", new Date(), "", "", 0, new Date(), new Date());
            listTeachers.add(teacher);

            List<TeacherResponse> response = new ArrayList<>();
            response.add(new TeacherResponse(teacher));

            //when

            when(teacherRepository.findAllByIsDeleted( 0)).thenReturn(listTeachers);

            ServiceResponse<List<TeacherResponse>> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);

            ServiceResponse<List<TeacherResponse>> actual = teacherServiceImp.listTeachers();

            assertEquals(expected, actual);
        }
    }
    @Nested
    class findListTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given

            List<Long> listId = new ArrayList<>();
            listId.add(1L);
            List<Teacher> listTeachers = new ArrayList<>();
            Teacher teacher = new Teacher(1L, "c1", "", new Date(), "", "", 0, new Date(), new Date());
            listTeachers.add(teacher);

            List<TeacherResponse> response = new ArrayList<>();
            response.add(new TeacherResponse(teacher));

            //when

            when(teacherRepository.findByIdInAndIsDeleted( listId, 0)).thenReturn(listTeachers);

            ServiceResponse<List<TeacherResponse>> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);

            ServiceResponse<List<TeacherResponse>> actual = teacherServiceImp.findListTeachers(listId);

            assertEquals(expected, actual);
        }
    }


}