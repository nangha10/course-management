package com.example.courseservice.service.implement;

import com.example.courseservice.dto.Constants;
import com.example.courseservice.dto.MessageCode;
import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.lesson.LessonCreateRequest;
import com.example.courseservice.dto.request.lesson.RollCallStatus;
import com.example.courseservice.dto.response.course.CourseInf;
import com.example.courseservice.dto.response.course.StudentReponse;
import com.example.courseservice.dto.response.course.TeacherInf;
import com.example.courseservice.dto.response.course.TeacherResponse;
import com.example.courseservice.dto.response.lesson.*;
import com.example.courseservice.entity.Course;
import com.example.courseservice.entity.Lesson;
import com.example.courseservice.entity.RollCall;
import com.example.courseservice.repository.CourseRepository;
import com.example.courseservice.repository.LessonRepository;
import com.example.courseservice.repository.RollCallRepository;
import com.example.courseservice.service.CommonService;
import com.example.courseservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RollCallRepository rollCallRepository;

    @Autowired
    CommonService commonService;

    @Override
    public ServiceResponse<LessonReponse> create(LessonCreateRequest lessonCreateRequest) {
        Lesson lesson = new Lesson();
        lesson.setId(lessonCreateRequest.getId());
        lesson.setTitle(lessonCreateRequest.getTitle());
        lesson.setDate(lessonCreateRequest.getDate());
        lesson.setCourseId(lessonCreateRequest.getCourseId());
        lesson.setTeacherId(lessonCreateRequest.getTeacherId());
        lesson.setDescription(lessonCreateRequest.getDescription());
        lesson.setCreatedDate(new Date());
        lesson.setUpdatedDate(new Date());
        lesson.setIsDeleted(Constants.NOT_DELETED);
        lessonRepository.save(lesson);

        LessonReponse lessonReponse = new LessonReponse(lesson);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, lessonReponse);
    }

    @Override
    public ServiceResponse<LessonReponse> update(LessonCreateRequest lessonCreateRequest) {
        Optional<Lesson> findLesson = lessonRepository.findByIdAndIsDeleted(lessonCreateRequest.getId(), Constants.NOT_DELETED);
        if (findLesson.isEmpty()) {
            return new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);
        }
        Lesson lesson = findLesson.get();
        lesson.setTitle(lessonCreateRequest.getTitle());
        lesson.setDate(lessonCreateRequest.getDate());
        lesson.setCourseId(lessonCreateRequest.getCourseId());
        lesson.setTeacherId(lessonCreateRequest.getTeacherId());
        lesson.setDescription(lessonCreateRequest.getDescription());
        lesson.setCreatedDate(new Date());
        lesson.setUpdatedDate(new Date());
        lesson.setIsDeleted(Constants.NOT_DELETED);
        lessonRepository.save(lesson);

        LessonReponse lessonReponse = new LessonReponse(lesson);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, lessonReponse);
    }

    @Override
    public ServiceResponse<LessonDetailResponse> lessonDetail(Long id) {
        Optional<Lesson> findLesson = lessonRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findLesson.isEmpty()) {
            return new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);
        }
        Lesson lesson = findLesson.get();
        LessonDetailResponse lessonDetailResponse = new LessonDetailResponse();
        lessonDetailResponse.setId(id);
        lessonDetailResponse.setTitle(lesson.getTitle());
        lessonDetailResponse.setDate(lesson.getDate());
        lessonDetailResponse.setDescription(lesson.getDescription());

        TeacherResponse teacherResponse = commonService.detailTeacher(lesson.getTeacherId());
        TeacherInf teacherInf = new TeacherInf(teacherResponse.getId(),teacherResponse.getName());
        lessonDetailResponse.setTeacherInf(teacherInf);

        Optional<Course> findCourse = courseRepository.findByIdAndIsDeleted(lesson.getCourseId(), Constants.NOT_DELETED);
        if (findCourse.isEmpty()) {
            return new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);
        }
        Course course = findCourse.get();
        CourseInf courseInf = new CourseInf(course.getId(),course.getName());
        lessonDetailResponse.setCourseInf(courseInf);

        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, lessonDetailResponse);
    }

    @Override
    public ServiceResponse<AllLessonResponse> allLesson(Long courseId) {
        AllLessonResponse allLessonResponse = new AllLessonResponse();
        Optional<Course> findCourse = courseRepository.findByIdAndIsDeleted(courseId, Constants.NOT_DELETED);
        if (findCourse.isEmpty()) {
            return new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);
        }
        Course course = findCourse.get();
        CourseInf courseInf = new CourseInf(course.getId(),course.getName());
        allLessonResponse.setCourseInf(courseInf);

        List<LessonReponse> listLessonResponse = new ArrayList<>();
        List<Lesson> allLesson = lessonRepository.findAllByIsDeletedAndCourseId(Constants.NOT_DELETED, courseId);
        for (Lesson lesson: allLesson) {
            LessonReponse lessonReponse = new LessonReponse();
            lessonReponse.setId(lesson.getId());
            lessonReponse.setTitle(lesson.getTitle());
            lessonReponse.setDate(lesson.getDate());

            TeacherResponse teacherResponse = commonService.detailTeacher(lesson.getTeacherId());
            lessonReponse.setTeacherId(teacherResponse.getId());
            listLessonResponse.add(lessonReponse);
        }
        allLessonResponse.setListLesson(listLessonResponse);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, allLessonResponse);
    }

    @Override
    public ServiceResponse<LessonReponse> delete(Long id) {
        Optional<Lesson> findLesson = lessonRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findLesson.isEmpty()) {
            return new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);
        }
        Lesson lesson = findLesson.get();
        lesson.setIsDeleted(Constants.IS_DELETED);
        lessonRepository.save(lesson);
        LessonReponse lessonReponse = new LessonReponse(lesson);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, lessonReponse);
    }

    @Override
    public ServiceResponse<Boolean> rollCall(Long lessonId, List<RollCallStatus> studentId) {
        List<RollCall> listRollCall = new ArrayList<>();
        for (RollCallStatus student: studentId) {
            Optional<RollCall> findRollCall = rollCallRepository.findByLessonIdAndStudentIdAndIsDeleted(lessonId, student.getIdStudent(), Constants.NOT_DELETED);
            if(findRollCall.isPresent()) {
                findRollCall.get().setStatus(student.getStatus());
                listRollCall.add(findRollCall.get());
            }
            else {
                RollCall rollcall = new RollCall();
                rollcall.setLessonId(lessonId);
                rollcall.setStudentId(student.getIdStudent());
                rollcall.setStatus(student.getStatus());
                rollcall.setCreatedDate(new Date());
                rollcall.setUpdatedDate(new Date());
                rollcall.setIsDeleted(Constants.NOT_DELETED);
                listRollCall.add(rollcall);
            }
        }
        rollCallRepository.saveAll(listRollCall);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);
    }

    @Override
    public ServiceResponse<RollCallResponse> viewRollCall(Long lessonId) {
        RollCallResponse rollCallResponse = new RollCallResponse();
        Optional<Lesson> findLesson = lessonRepository.findByIdAndIsDeleted(lessonId, Constants.NOT_DELETED);
        if (findLesson.isEmpty()) {
            return new ServiceResponse(MessageCode.LESSON_NOT_EXISTED, MessageCode.LESSON_NOT_EXISTED_MESSAGE, null);
        }
        Lesson lesson = findLesson.get();
        rollCallResponse.setLessonId(lessonId);
        rollCallResponse.setDate(lesson.getDate());

        List<RollCallInf> rollCallInfList = new ArrayList<>();
        List<RollCall> rollCallList = rollCallRepository.findByLessonIdAndIsDeleted(lessonId, Constants.NOT_DELETED);
        for (RollCall rollCall: rollCallList) {
            StudentReponse studentResponse = commonService.detailStudent(rollCall.getStudentId());
            RollCallInf rollCallInf = new RollCallInf();
            rollCallInf.setStudentId(studentResponse.getId());
            rollCallInf.setStudentName(studentResponse.getName());
            rollCallInf.setDob(studentResponse.getDob());
            rollCallInf.setEmail(studentResponse.getEmail());
            rollCallInf.setStatus(rollCall.getStatus());
            rollCallInfList.add(rollCallInf);
        }
        rollCallResponse.setRollCallInf(rollCallInfList);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, rollCallResponse);
    }
}
