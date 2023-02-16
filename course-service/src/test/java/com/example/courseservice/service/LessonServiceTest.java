package com.example.courseservice.service;

import com.example.courseservice.dto.MessageCode;
import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.lesson.LessonCreateRequest;
import com.example.courseservice.dto.request.lesson.RollCallStatus;
import com.example.courseservice.dto.response.course.CourseInf;
import com.example.courseservice.dto.response.course.StudentReponse;
import com.example.courseservice.dto.response.course.TeacherInf;
import com.example.courseservice.dto.response.course.TeacherResponse;
import com.example.courseservice.dto.response.lesson.*;
import com.example.courseservice.entity.*;
import com.example.courseservice.repository.*;
import com.example.courseservice.service.implement.CommonServiceImpl;
import com.example.courseservice.service.implement.LessonServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    LessonRepository lessonRepository;

    @InjectMocks
    LessonServiceImpl lessonServiceImp;

    @Mock
    CommonServiceImpl commonServiceImpl;

    @Mock
    RollCallRepository rollCallRepository;

    @Mock
    CourseRepository courseRepository;

    @Nested
    class createTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            LessonCreateRequest lessonCreateRequest = new LessonCreateRequest();
            lessonCreateRequest.setId(1L);

            Lesson lesson = new Lesson();
            lesson.setId(1L);

            LessonReponse lessonReponse = new LessonReponse(lesson);

            when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

            ServiceResponse<LessonReponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, lessonReponse);

            ServiceResponse<LessonReponse> actual = lessonServiceImp.create(lessonCreateRequest);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class updateTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            LessonCreateRequest lessonCreateRequest = new LessonCreateRequest();
            lessonCreateRequest.setId(1L);

            Lesson lesson = new Lesson();
            lesson.setId(1L);

            LessonReponse lessonReponse = new LessonReponse(lesson);

            when(lessonRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(lesson));
            when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

            ServiceResponse<LessonReponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, lessonReponse);

            ServiceResponse<LessonReponse> actual = lessonServiceImp.update(lessonCreateRequest);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnSuccess_WhenFindLessonEmpty() {
            //given
            LessonCreateRequest lessonCreateRequest = new LessonCreateRequest();
            lessonCreateRequest.setId(1L);

            //when
            when(lessonRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.empty());

            ServiceResponse<LessonReponse> expected = new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<LessonReponse> actual = lessonServiceImp.update(lessonCreateRequest);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class detailTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            Long id = 1L;

            Lesson lesson = new Lesson();
            lesson.setId(1L);
            lesson.setTeacherId(1L);
            lesson.setCourseId(1L);

            LessonReponse lessonReponse = new LessonReponse(lesson);
            TeacherInf teacherInf = new TeacherInf(1L, "T1");

            TeacherResponse teacherResponse = new TeacherResponse();
            teacherResponse.setId(1L);
            teacherResponse.setName("T1");

            Course course = new Course();
            course.setId(1L);
            course.setName("C1");
            CourseInf courseInf = new CourseInf(1L,"C1");

            LessonDetailResponse lessonDetailResponse = new LessonDetailResponse();
            lessonDetailResponse.setId(id);
            lessonDetailResponse.setCourseInf(courseInf);
            lessonDetailResponse.setTeacherInf(teacherInf);

            when(lessonRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(lesson));
            when(commonServiceImpl.detailTeacher(1L)).thenReturn(teacherResponse);
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(course));

            ServiceResponse<LessonDetailResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, lessonDetailResponse);

            ServiceResponse<LessonDetailResponse> actual = lessonServiceImp.lessonDetail(id);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnSuccess_WhenFindLessonEmpty() {
            //given
            Long id = 1L;

            //when
            when(lessonRepository.findByIdAndIsDeleted(id,0)).thenReturn(Optional.empty());

            ServiceResponse<LessonDetailResponse> expected = new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<LessonDetailResponse> actual = lessonServiceImp.lessonDetail(id);

            assertEquals(expected, actual);
        }
        @Test
        void should_ReturnSuccess_WhenFindCourseEmpty() {
            Long id = 1L;

            Lesson lesson = new Lesson();
            lesson.setId(1L);
            lesson.setTeacherId(1L);
            lesson.setCourseId(1L);

            LessonReponse lessonReponse = new LessonReponse(lesson);
            TeacherInf teacherInf = new TeacherInf(1L, "T1");

            TeacherResponse teacherResponse = new TeacherResponse();
            teacherResponse.setId(1L);
            teacherResponse.setName("T1");

            Course course = new Course();
            course.setId(1L);
            course.setName("C1");
            CourseInf courseInf = new CourseInf(1L,"C1");

            LessonDetailResponse lessonDetailResponse = new LessonDetailResponse();
            lessonDetailResponse.setId(id);
            lessonDetailResponse.setCourseInf(courseInf);
            lessonDetailResponse.setTeacherInf(teacherInf);

            when(lessonRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(lesson));
            when(commonServiceImpl.detailTeacher(1L)).thenReturn(teacherResponse);
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.empty());

            ServiceResponse<LessonDetailResponse> expected = new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<LessonDetailResponse> actual = lessonServiceImp.lessonDetail(id);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class allCourseTests{
        @Test
        void should_ReturnSuccess_WhenCalled() {
            //given
            Lesson lesson1 = new Lesson(1L, "c1", 1L, 1L, new Date(), "", 0, new Date(), new Date());
            Lesson lesson2 = new Lesson(2L, "c2", 1L, 1L, new Date(), "new Date()", 0, new Date(), new Date());

            Course course = new Course();
            course.setId(1L);
            course.setName("C1");
            CourseInf courseInf = new CourseInf(1L,"C1");

            AllLessonResponse allLessonResponse = new AllLessonResponse();
            allLessonResponse.setCourseInf(courseInf);

            List<LessonReponse> lessonResponseList = new ArrayList<>();
            lessonResponseList.add(new LessonReponse(lesson1));
            lessonResponseList.add(new LessonReponse(lesson2));

            List<Lesson> listLesson = new ArrayList<>();
            listLesson.add(lesson1);
            listLesson.add(lesson2);

            TeacherResponse teacherResponse = new TeacherResponse();
            teacherResponse.setId(1L);
            teacherResponse.setName("T1");

            allLessonResponse.setListLesson(lessonResponseList);

            //when
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(course));
            when(lessonRepository.findAllByIsDeletedAndCourseId(0,1L)).thenReturn(listLesson);
            when(commonServiceImpl.detailTeacher(1L)).thenReturn(teacherResponse);

            ServiceResponse<AllLessonResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, allLessonResponse);

            ServiceResponse<AllLessonResponse> actual = lessonServiceImp.allLesson(1L);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnSuccess_WhenCourseEmpty() {
            //given

            Course course = new Course();
            course.setId(1L);
            course.setName("C1");
            CourseInf courseInf = new CourseInf(1L,"C1");

            //when
            when(courseRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.empty());

            ServiceResponse<AllLessonResponse> expected = new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<AllLessonResponse> actual = lessonServiceImp.allLesson(1L);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class deleteTests{
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            //given
            Long id = 1L;
            Lesson lesson = new Lesson(1L, "c1", 1L, 1L, new Date(), "", 0, new Date(), new Date());
            List<StudentList> studentList = new ArrayList<>();
            List<TeacherCourse> listTeachers = new ArrayList<>();
            //when

            when(lessonRepository.findByIdAndIsDeleted(1L,0)).thenReturn(Optional.of(lesson));

            ServiceResponse<LessonReponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, new LessonReponse(lesson));

            ServiceResponse<LessonReponse> actual = lessonServiceImp.delete(id);

            assertEquals(expected, actual);

        }
        @Test
        void should_ReturnError_WhenStudentNotExist() {
            //given
            Long id = 1L;
            //when
            when(lessonRepository.findByIdAndIsDeleted(id, 0)).thenReturn(Optional.empty());

            ServiceResponse<LessonReponse> expected = new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<LessonReponse> actual = lessonServiceImp.delete(id);

            assertEquals(expected, actual);
        }
    }
    @Nested
    class rollCallTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {

            RollCall rollCall = new RollCall(1L, 1L, 1L, 1, 0, new Date(), new Date());
            Long lessonId = 1L;
            List<RollCallStatus> studentsId = new ArrayList<>();
            studentsId.add(new RollCallStatus(1L,1));

            when(rollCallRepository.findByLessonIdAndStudentIdAndIsDeleted(1L, 1L,0)).thenReturn(Optional.of(rollCall));

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);

            ServiceResponse<Boolean> actual = lessonServiceImp.rollCall(lessonId, studentsId);

            assertEquals(expected, actual);
        }

        @Test
        void should_ReturnSuccess_WhenRollCallNotExist() {

            Long lessonId = 1L;
            List<RollCallStatus> studentsId = new ArrayList<>();
            studentsId.add(new RollCallStatus(1L,1));

            when(rollCallRepository.findByLessonIdAndStudentIdAndIsDeleted(1L, 1L,0)).thenReturn(Optional.empty());

            ServiceResponse<Boolean> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);

            ServiceResponse<Boolean> actual = lessonServiceImp.rollCall(lessonId, studentsId);

            assertEquals(expected, actual);


        }
    }

    @Nested
    class viewRollCallTests {
        @Test
        void should_ReturnSuccess_WhenCorrectInput() {
            RollCallResponse rollCallResponse = new RollCallResponse();
            StudentReponse student = new StudentReponse(1L, "S1", "e1", new Date(), "", 11, "");

            RollCall rollCall = new RollCall(1L, 1L, 1L, 0, 0, new Date(), new Date());
            Long lessonId = 1L;
            List<RollCallStatus> studentsId = new ArrayList<>();
            studentsId.add(new RollCallStatus(1L,0));
            List<RollCall> rollCallList = new ArrayList<>();
            rollCallList.add(rollCall);

            Lesson lesson = new Lesson(1L, "c1", 1L, 1L, new Date(), "", 0, new Date(), new Date());
            rollCallResponse.setLessonId(lessonId);
            rollCallResponse.setDate(lesson.getDate());
            List<RollCallInf> rollCallInfList = new ArrayList<>();
            rollCallInfList.add(new RollCallInf(1L, "S1", new Date(), "e1", 0));
            rollCallResponse.setRollCallInf(rollCallInfList);

            when(rollCallRepository.findByLessonIdAndIsDeleted(1L, 0)).thenReturn(rollCallList);
            when(lessonRepository.findByIdAndIsDeleted(lessonId, 0)).thenReturn(Optional.of(lesson));
            when(commonServiceImpl.detailStudent(1L)).thenReturn(student);

            ServiceResponse<RollCallResponse> expected = new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, rollCallResponse);

            ServiceResponse<RollCallResponse> actual = lessonServiceImp.viewRollCall(lessonId);

            assertEquals(expected.getCode(), actual.getCode());


        }

        @Test
        void should_ReturnSuccess_WhenLessonNotExist() {

            Long lessonId = 1L;

            when(lessonRepository.findByIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());

            ServiceResponse<RollCallResponse> expected = new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);

            ServiceResponse<RollCallResponse> actual = lessonServiceImp.viewRollCall(lessonId);

            assertEquals(expected, actual);


        }
    }

}