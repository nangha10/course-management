package com.example.courseservice.service.implement;

import com.example.courseservice.dto.Constants;
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
import com.example.courseservice.feignclients.TeacherFeignClient;
import com.example.courseservice.repository.CourseRepository;
import com.example.courseservice.repository.LessonRepository;
import com.example.courseservice.repository.RollCallRepository;
import com.example.courseservice.service.CommonService;
import com.example.courseservice.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private static final Logger logger = LoggerFactory.getLogger(LessonServiceImpl.class);

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherFeignClient teacherFeignClient;

    @Autowired
    private RollCallRepository rollCallRepository;

    @Autowired
    CommonService commonService;

    @Override
    public String create(LessonCreateRequest lessonCreateRequest) {
        Optional<Lesson> findLesson = lessonRepository.findByIdAndIsDeleted(lessonCreateRequest.getId(), Constants.NOT_DELETED);
        if (findLesson.isPresent()) {
            return ("Mã buổi học đã tồn tại");
        }
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
        return ("Them moi buoi hoc thanh cong");
    }

    @Override
    public String update(LessonCreateRequest lessonCreateRequest) {
        Lesson lesson = lessonRepository.findByIdAndIsDeleted(lessonCreateRequest.getId(), Constants.NOT_DELETED).get();
        lesson.setTitle(lessonCreateRequest.getTitle());
        lesson.setDate(lessonCreateRequest.getDate());
        lesson.setCourseId(lessonCreateRequest.getCourseId());
        lesson.setTeacherId(lessonCreateRequest.getTeacherId());
        lesson.setDescription(lessonCreateRequest.getDescription());
        lesson.setCreatedDate(new Date());
        lesson.setUpdatedDate(new Date());
        lesson.setIsDeleted(Constants.NOT_DELETED);
        lessonRepository.save(lesson);
        return ("Cap nhat buoi hoc thanh cong");
    }

    @Override
    public LessonDetailResponse lessonDetail(Long id) {
        Lesson lesson = lessonRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED).get();
        LessonDetailResponse lessonDetailResponse = new LessonDetailResponse();
        lessonDetailResponse.setId(id);
        lessonDetailResponse.setTitle(lesson.getTitle());
        lessonDetailResponse.setDate(lesson.getDate());
        lessonDetailResponse.setDescription(lesson.getDescription());

        TeacherResponse teacherResponse = commonService.detailTeacher(lesson.getTeacherId());
        TeacherInf teacherInf = new TeacherInf(teacherResponse.getId(),teacherResponse.getName());
        lessonDetailResponse.setTeacherInf(teacherInf);

        Course course = courseRepository.findByIdAndIsDeleted(lesson.getCourseId(), Constants.NOT_DELETED).get();
        CourseInf courseInf = new CourseInf(course.getId(),course.getName());
        lessonDetailResponse.setCourseInf(courseInf);

        return lessonDetailResponse;
    }

    @Override
    public AllLessonResponse allLesson(Long courseId) {
        AllLessonResponse allLessonResponse = new AllLessonResponse();

        Course course = courseRepository.findByIdAndIsDeleted(courseId, Constants.NOT_DELETED).get();
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
            lessonReponse.setTeacherName(teacherResponse.getName());
            listLessonResponse.add(lessonReponse);
        }
        allLessonResponse.setListLesson(listLessonResponse);
        return allLessonResponse;
    }

    @Override
    public String delete(Long id) {
        Lesson lesson = lessonRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED).get();
        lesson.setIsDeleted(Constants.IS_DELETED);
        lessonRepository.save(lesson);
        return ("Xoa buoi hoc thanh cong");
    }

    @Override
    public String rollCall(Long lessonId, List<RollCallStatus> studentId) {
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
        return ("Diem danh thanh cong");
    }

    @Override
    public RollCallResponse viewRollCall(Long lessonId) {
        RollCallResponse rollCallResponse = new RollCallResponse();

        Lesson lesson = lessonRepository.findByIdAndIsDeleted(lessonId, Constants.NOT_DELETED).get();
        rollCallResponse.setLessonId(lessonId);
        rollCallResponse.setDate(lesson.getDate());

        List<RollCallInf> rollCallInfList = new ArrayList<>();
        List<RollCall> rollCallList = rollCallRepository.findByLessonIdAndIsDeleted(lessonId, Constants.NOT_DELETED);
        for (RollCall rollCall: rollCallList){
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
        return rollCallResponse;
    }
}
