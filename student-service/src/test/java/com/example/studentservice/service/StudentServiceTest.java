package com.example.studentservice.service;

import com.example.studentservice.dto.MessageCode;
import com.example.studentservice.dto.ServiceResponse;
import com.example.studentservice.dto.request.CreateStudentRequest;
import com.example.studentservice.dto.response.StudentResponse;
import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.service.implement.StudentServiceImpl;
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
class StudentServiceTest {
    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentServiceImpl studentServiceImp;

    @Nested
    class createTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            CreateStudentRequest createStudentRequest = new CreateStudentRequest();

            Student student = new Student();
            student.setId(1L);

            StudentResponse studentResponse = new StudentResponse(student);

            when(studentRepository.save(any(Student.class))).thenReturn(student);

            ServiceResponse<StudentResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, studentResponse);

            ServiceResponse<StudentResponse> actual = studentServiceImp.create(createStudentRequest);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class updateTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            CreateStudentRequest createStudentRequest = new CreateStudentRequest();
            createStudentRequest.setId(1L);

            Student student = new Student();
            student.setId(1L);

            StudentResponse studentResponse = new StudentResponse(student);

            when(studentRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(student));
            when(studentRepository.save(any(Student.class))).thenReturn(student);

            ServiceResponse<StudentResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, studentResponse);

            ServiceResponse<StudentResponse> actual = studentServiceImp.update(createStudentRequest);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnSuccess_WhenStudentNotExist() {
            CreateStudentRequest createStudentRequest = new CreateStudentRequest();
            createStudentRequest.setId(1L);

            when(studentRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());
            ServiceResponse<StudentResponse> expected = new ServiceResponse(MessageCode.STUDENT_NOT_EXISTED, MessageCode.STUDENT_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<StudentResponse> actual = studentServiceImp.update(createStudentRequest);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class detailTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            Long id = 1L;
            Student student = new Student();
            student.setId(1L);

            StudentResponse studentResponse = new StudentResponse(student);

            when(studentRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(student));

            ServiceResponse<StudentResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, studentResponse);

            ServiceResponse<StudentResponse> actual = studentServiceImp.detail(id);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnSuccess_WhenTeacherNotExist() {
            Long id = 1L;
            when(studentRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());
            ServiceResponse<StudentResponse> expected = new ServiceResponse(MessageCode.STUDENT_NOT_EXISTED, MessageCode.STUDENT_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<StudentResponse> actual = studentServiceImp.detail(id);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class deleteTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long id = 1L;
            Student student = new Student(1L, "c1", "", new Date(), "", 11, "", 0, new Date(), new Date());
            //when

            when(studentRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(student));

            ServiceResponse<StudentResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, new StudentResponse(student));

            ServiceResponse<StudentResponse> actual = studentServiceImp.delete(id);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnError_WhenStudentNotExist() {
            //given
            Long id = 1L;
            //when
            when(studentRepository.findByIdAndIsDeleted(id, 0)).thenReturn(Optional.empty());

            ServiceResponse<StudentResponse> expected = new ServiceResponse(MessageCode.STUDENT_NOT_EXISTED, MessageCode.STUDENT_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<StudentResponse> actual = studentServiceImp.delete(id);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class listTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            List<Student> listStudents = new ArrayList<>();
            Student student = new Student(1L, "c1", "", new Date(), "", 11, "", 0, new Date(), new Date());
            listStudents.add(student);

            List<StudentResponse> response = new ArrayList<>();
            response.add(new StudentResponse(student));

            //when

            when(studentRepository.findAllByIsDeleted( 0)).thenReturn(listStudents);

            ServiceResponse<List<StudentResponse>> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);

            ServiceResponse<List<StudentResponse>> actual = studentServiceImp.listStudents();

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
            List<Student> listStudents = new ArrayList<>();
            Student student = new Student(1L, "c1", "", new Date(), "", 11, "", 0, new Date(), new Date());
            listStudents.add(student);

            List<StudentResponse> response = new ArrayList<>();
            response.add(new StudentResponse(student));

            //when

            when(studentRepository.findByIdInAndIsDeleted( listId, 0)).thenReturn(listStudents);

            ServiceResponse<List<StudentResponse>> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);

            ServiceResponse<List<StudentResponse>> actual = studentServiceImp.findListStudents(listId);

            assertEquals(expected, actual);
        }
    }

}