package com.example.courseservice.service;

import com.example.courseservice.dto.MessageCode;
import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.request.course.TuitionStatus;
import com.example.courseservice.dto.response.course.*;
import com.example.courseservice.entity.Course;
import com.example.courseservice.entity.StudentList;
import com.example.courseservice.entity.TeacherCourse;
import com.example.courseservice.repository.CourseRepository;
import com.example.courseservice.repository.StudentListRepository;
import com.example.courseservice.repository.TeacherCourseRepository;
import com.example.courseservice.service.implement.CommonServiceImpl;
import com.example.courseservice.service.implement.CourseServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseServiceImpl courseServiceImp;

    @Mock
    CommonServiceImpl commonServiceImpl;

    @Mock
    TeacherCourseRepository teacherCourseRepository;

    @Mock
    StudentListRepository studentListRepository;


    @Nested
    class detailCourseTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long id = 1L;
            List<Long> listTeacherId = new ArrayList<>();
            listTeacherId.add(1L);
            listTeacherId.add(2L);

            Course course = new Course();
            course.setId(1L);

            List<TeacherCourse> listTeacherCourse = new ArrayList<>();
            listTeacherCourse.add(new TeacherCourse(1L, 1L,1L, 0, new Date(), new Date()));
            listTeacherCourse.add(new TeacherCourse(2L, 1L,2L, 0, new Date(), new Date()));

            List<TeacherResponse> listTeacherResponse = new ArrayList<>();
            listTeacherResponse.add(new TeacherResponse(1L, "n1", "e1", new Date(), "01223", "ee1", new Date(), new Date()));
            listTeacherResponse.add(new TeacherResponse(2L, "n2", "e2", new Date(), "01224", "ee2", new Date(), new Date()));

            List<TeacherInf> listTeacherInf = new ArrayList<>();
            listTeacherInf.add(new TeacherInf(1L, "n1"));
            listTeacherInf.add(new TeacherInf(2L, "n2"));

            CourseDetailResponse courseDetailResponse = new CourseDetailResponse();
            courseDetailResponse.setId(1L);
            courseDetailResponse.setListTeachers(listTeacherInf);
            //when
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(course));
            when(teacherCourseRepository.findByCourseIdAndIsDeleted(1L,0)).thenReturn(listTeacherCourse);
            when(commonServiceImpl.listTeachers(listTeacherId)).thenReturn(listTeacherResponse);

            ServiceResponse<CourseDetailResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseDetailResponse);

            ServiceResponse<CourseDetailResponse> actual = courseServiceImp.courseDetail(id);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnSuccess_WhenFindCourseEmpty() {
            //given
            Long id = 1L;

            //when
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.empty());

            ServiceResponse<CourseDetailResponse> expected = new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<CourseDetailResponse> actual = courseServiceImp.courseDetail(id);

            assertEquals(expected, actual);
        }

    }

    @Nested
    class createTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            CourseCreateRequest courseCreateRequest = new CourseCreateRequest();
            courseCreateRequest.setId(1L);
            List<Long> listTeacherId = new ArrayList<>();
            listTeacherId.add(2L);
            courseCreateRequest.setListTeacherId(listTeacherId);

            CourseReponse courseReponse = new CourseReponse();
            courseReponse.setId(1L);

            Course course = new Course();
            course.setId(1L);

            when(courseRepository.save(any(Course.class))).thenReturn(course);

            ServiceResponse<CourseReponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse);

            ServiceResponse<CourseReponse> actual = courseServiceImp.create(courseCreateRequest);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class updateTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInputAndListTeacherNotUpdated() {
            //given
            Long id = 1L;
            CourseCreateRequest courseCreateRequest = new CourseCreateRequest();
            List<Long> listTeacherId = new ArrayList<>();
            listTeacherId.add(2L);
            courseCreateRequest.setListTeacherId(listTeacherId);

            CourseReponse courseReponse = new CourseReponse();
            courseReponse.setId(1L);

            Course course = new Course();
            course.setId(1L);

            List<TeacherCourse> listTeachersNotUpdate = new ArrayList<>();
            TeacherCourse teacherCourse = new TeacherCourse(1L, 1L,2L, 0, new Date(), new Date());
            listTeachersNotUpdate.add(teacherCourse);
            //when
            when(courseRepository.findByIdAndIsDeleted(id,0)).thenReturn(Optional.of(course));
            when(courseRepository.save(any(Course.class))).thenReturn(course);
            when(teacherCourseRepository.findByCourseIdAndIsDeleted(1L,0)).thenReturn(listTeachersNotUpdate);

            ServiceResponse<CourseReponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse);

            ServiceResponse<CourseReponse> actual = courseServiceImp.update(id, courseCreateRequest);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnEmpty_WhenFindCourseEmpty() {
            //given
            Long id = 1L;
            CourseCreateRequest courseCreateRequest = new CourseCreateRequest();

            Course course = new Course();
            //when
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.empty());

            ServiceResponse<CourseReponse> expected = new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<CourseReponse> actual = courseServiceImp.update(id, courseCreateRequest);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnSuccess_WhenAddTeacherIsDeleted() {
            //given
            Long id = 1L;

            CourseCreateRequest courseCreateRequest = new CourseCreateRequest();
            List<Long> listTeacherId = new ArrayList<>();
            listTeacherId.add(1L);
            courseCreateRequest.setListTeacherId(listTeacherId);

            CourseReponse courseReponse = new CourseReponse();
            courseReponse.setId(1L);

            List<TeacherCourse> listTeachersNotUpdate = new ArrayList<>();
            TeacherCourse teacherCourse = new TeacherCourse(1L, 1L,2L, 1, new Date(), new Date());
            listTeachersNotUpdate.add(teacherCourse);

            List<TeacherCourse> listTeachersUpdated = new ArrayList<>();
            listTeachersUpdated.add(teacherCourse);

            Course course = new Course();
            course.setId(1L);
            //when
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(course));
            when(courseRepository.save(any(Course.class))).thenReturn(course);
            when(teacherCourseRepository.findByTeacherIdAndCourseId(1L,1L)).thenReturn(Optional.of(teacherCourse));

            ServiceResponse<CourseReponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse);

            ServiceResponse<CourseReponse> actual = courseServiceImp.update(id, courseCreateRequest);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class allCourseTests{
        @Test
        void should_ReturnSuccess_WhenCalled() {
            //given
            Course course1 = new Course(1L, "c1", new Date(), new Date(), 10L, 0, new Date(), new Date());
            Course course2 = new Course(2L, "c2", new Date(), new Date(), 10L, 0, new Date(), new Date());

            List<CourseReponse> courseResponseList = new ArrayList<>();
            courseResponseList.add(new CourseReponse(course1));
            courseResponseList.add(new CourseReponse(course2));

            List<Course> listCourses = new ArrayList<>();
            listCourses.add(course1);
            listCourses.add(course2);

            //when
            when(courseRepository.findAllByIsDeleted(0)).thenReturn(listCourses);

            ServiceResponse< List<CourseReponse>> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseResponseList);

            ServiceResponse< List<CourseReponse>> actual = courseServiceImp.allCourse();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class deleteTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long id = 1L;
            Course course = new Course(1L, "c1", new Date(), new Date(), 10L, 0, new Date(), new Date());
            //when

            when(courseRepository.findByIdAndIsDeleted(id, 0)).thenReturn(Optional.of(course));
            when(studentListRepository.findByCourseIdAndIsDeleted(id,0)).thenReturn(new ArrayList<>());
            when(teacherCourseRepository.findByCourseIdAndIsDeleted(id,0)).thenReturn(new ArrayList<>());

            ServiceResponse<CourseReponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, new CourseReponse(course));

            ServiceResponse<CourseReponse> actual = courseServiceImp.delete(id);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnError_WhenStudentNotExist() {
            //given
            Long id = 1L;
            //when
            when(courseRepository.findByIdAndIsDeleted(id, 0)).thenReturn(Optional.empty());

            ServiceResponse<CourseReponse> expected = new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<CourseReponse> actual = courseServiceImp.delete(id);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class addStudentsTests{
        @Test
        void should_ReturnSuccess_WhenStudentNotPresent() {
            //given
            Long idCourse = 1L;
            List<Long> studentsId = new ArrayList<>();
            studentsId.add(1L);
            List<StudentList> listStudents = new ArrayList<>();
            StudentList studentList = new StudentList(1L,1L,1L,0,0, new Date(), new Date());
            listStudents.add(studentList);
            //when
            when(studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(idCourse, 1L,1)).thenReturn(Optional.of(studentList));

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);

            ServiceResponse<Boolean> actual = courseServiceImp.addStudentsToCourse(idCourse,studentsId);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnSuccess_WhenStudentIsDeleted() {
            //given
            Long idCourse = 1L;
            List<Long> studentsId = new ArrayList<>();
            studentsId.add(1L);
            //when
            when(studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(idCourse, 1L,1)).thenReturn(Optional.empty());

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);

            ServiceResponse<Boolean> actual = courseServiceImp.addStudentsToCourse(idCourse,studentsId);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class removeStudentsTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long idCourse = 1L;
            List<Long> studentsId = new ArrayList<>();
            studentsId.add(1L);
            List<StudentList> listStudents = new ArrayList<>();
            StudentList studentList = new StudentList(1L,1L,1L,0,0, new Date(), new Date());
            listStudents.add(studentList);
            //when
            when(studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(idCourse, 1L,0)).thenReturn(Optional.of(studentList));

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);

            ServiceResponse<Boolean> actual = courseServiceImp.removeStudentsFromCourse(idCourse,studentsId);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnError_WhenStudentNotExist() {
            //given
            Long idCourse = 1L;
            List<Long> studentsId = new ArrayList<>();
            studentsId.add(1L);
            //when
            when(studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(idCourse, 1L,0)).thenReturn(Optional.empty());

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.STUDENT_NOT_EXIST, MessageCode.STUDENT_NOT_EXIST_MESSAGE, false);

            ServiceResponse<Boolean> actual = courseServiceImp.removeStudentsFromCourse(idCourse,studentsId);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class tuitionStatusTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long idCourse = 1L;
            List<Long> studentsId = new ArrayList<>();
            studentsId.add(1L);
            List<StudentList> listStudents = new ArrayList<>();

            List<TuitionStatus> listTuitionStatus = new ArrayList<>();
            listTuitionStatus.add(new TuitionStatus(1L, 0));

            StudentList studentList = new StudentList(1L,1L,1L,0,0, new Date(), new Date());
            listStudents.add(studentList);
            //when
            when(studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(idCourse, 1L,0)).thenReturn(Optional.of(studentList));

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);

            ServiceResponse<Boolean> actual = courseServiceImp.updateTuitionStatus(idCourse,listTuitionStatus);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnError_WhenStudentNotExist() {
            //given
            Long idCourse = 1L;
            List<Long> studentsId = new ArrayList<>();
            studentsId.add(1L);

            List<TuitionStatus> listTuitionStatus = new ArrayList<>();
            listTuitionStatus.add(new TuitionStatus(1L, 0));
            //when
            when(studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(idCourse, 1L,0)).thenReturn(Optional.empty());

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.STUDENT_NOT_EXIST, MessageCode.STUDENT_NOT_EXIST_MESSAGE, false);

            ServiceResponse<Boolean> actual = courseServiceImp.updateTuitionStatus(idCourse,listTuitionStatus);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class listStudentsTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long courseId = 1L;
            List<Long> studentsId = new ArrayList<>();
            studentsId.add(1L);
            List<StudentList> listStudents = new ArrayList<>();

            StudentList studentList = new StudentList(1L,1L,1L,0,0, new Date(), new Date());
            listStudents.add(studentList);

            List<StudentReponse> studentReponseList = new ArrayList<>();
            studentReponseList.add( new StudentReponse(1L,"S1", "e1", new Date(), "", 10, ""));

            List<StudentInf> studentInfList = new ArrayList<>();
            studentInfList.add(new StudentInf(1L, "S1", "e1", new Date(), 0));
            //when
            when(studentListRepository.findByCourseIdAndIsDeleted(courseId, 0)).thenReturn(listStudents);
            when(commonServiceImpl.listStudents(studentsId)).thenReturn(studentReponseList);

            ServiceResponse<List<StudentInf>> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, studentInfList);

            ServiceResponse<List<StudentInf>> actual = courseServiceImp.listStudents(courseId);

            assertEquals(expected.getCode(), actual.getCode());
        }

    }

}