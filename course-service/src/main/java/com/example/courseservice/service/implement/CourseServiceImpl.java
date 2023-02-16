package com.example.courseservice.service.implement;

import com.example.courseservice.dto.Constants;
import com.example.courseservice.dto.MessageCode;
import com.example.courseservice.dto.ServiceResponse;
import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.request.course.TuitionStatus;
import com.example.courseservice.dto.response.course.*;
import com.example.courseservice.entity.Course;
import com.example.courseservice.entity.StudentList;
import com.example.courseservice.entity.TeacherCourse;
import com.example.courseservice.feignclients.StudentFeignClient;
import com.example.courseservice.feignclients.TeacherFeignClient;
import com.example.courseservice.repository.CourseRepository;
import com.example.courseservice.repository.StudentListRepository;
import com.example.courseservice.repository.TeacherCourseRepository;
import com.example.courseservice.service.CommonService;
import com.example.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private StudentListRepository studentListRepository;

    @Autowired
    private TeacherFeignClient teacherFeignClient;

    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    CommonService commonService;

    @Override
    public ServiceResponse<CourseReponse> create(CourseCreateRequest courseCreateRequest) {
        try {
            Course course = new Course();
            course.setName(courseCreateRequest.getName());
            course.setStartDate(courseCreateRequest.getStartDate());
            course.setEndDate(courseCreateRequest.getEndDate());
            course.setTuition(courseCreateRequest.getTuition());
            course.setCreatedDate(new Date());
            course.setUpdatedDate(new Date());
            course.setIsDeleted(Constants.NOT_DELETED);

            List<TeacherCourse> listTeachers = new ArrayList<>();
            for (Long teacherId:courseCreateRequest.getListTeacherId()) {
                TeacherCourse teacherCourse = new TeacherCourse();
                teacherCourse.setCourseId(course.getId());
                teacherCourse.setTeacherId(teacherId);
                teacherCourse.setCreatedDate(new Date());
                teacherCourse.setUpdatedDate(new Date());
                teacherCourse.setIsDeleted(Constants.NOT_DELETED);
                listTeachers.add(teacherCourse);
            }
            Course courseSaved = courseRepository.save(course);
            teacherCourseRepository.saveAll(listTeachers);

            CourseReponse courseReponse = new CourseReponse(courseSaved);

            return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse);
        } catch (Exception ex) {
            return new ServiceResponse(MessageCode.FAILED, ex.getMessage(), null);

        }

    }

    @Override
    public ServiceResponse<CourseReponse> update(Long id, CourseCreateRequest courseCreateRequest) {
        Optional<Course> findCourse = courseRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findCourse.isEmpty()) {
            return new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);
        }
        Course courseUpdate = findCourse.get();
        courseUpdate.setName(courseCreateRequest.getName());
        courseUpdate.setStartDate(courseCreateRequest.getStartDate());
        courseUpdate.setEndDate(courseCreateRequest.getEndDate());
        courseUpdate.setUpdatedDate(new Date());
        List<TeacherCourse> listTeachers = new ArrayList<>();
        List<TeacherCourse> listTeachersNotUpdate = teacherCourseRepository.findByCourseIdAndIsDeleted(id, Constants.NOT_DELETED);
        for (TeacherCourse teacher: listTeachersNotUpdate) {
            if(!Arrays.asList(listTeachersNotUpdate).contains(teacher)) {
                teacher.setIsDeleted(Constants.IS_DELETED);
                teacher.setUpdatedDate(new Date());
                listTeachers.add(teacher);
            }
        }
        for (Long teacherId: courseCreateRequest.getListTeacherId()) {
            Optional<TeacherCourse> findTeacher = teacherCourseRepository.findByTeacherIdAndCourseId(teacherId, courseUpdate.getId());
            TeacherCourse teacher = new TeacherCourse();
            if (findTeacher.isEmpty()) {
                teacher.setCourseId(courseUpdate.getId());
                teacher.setTeacherId(teacherId);
                teacher.setCreatedDate(new Date());
                teacher.setUpdatedDate(new Date());
                teacher.setIsDeleted(Constants.NOT_DELETED);
                listTeachers.add(teacher);
            } else {
                teacher = findTeacher.get();
                if (teacher.getIsDeleted() == Constants.IS_DELETED) {
                    teacher.setIsDeleted(Constants.NOT_DELETED);
                    teacher.setUpdatedDate(new Date());
                }
                listTeachers.add(teacher);
            }
        }
        Course courseUpdated = courseRepository.save(courseUpdate);
        teacherCourseRepository.saveAll(listTeachers);

        CourseReponse courseReponse = new CourseReponse(courseUpdated);

        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse);
    }

    @Override
    public ServiceResponse<CourseDetailResponse> courseDetail(Long id) {
        Optional<Course> findCourse = courseRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findCourse.isEmpty()) {
            return new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);
        }
        Course courseInf = findCourse.get();
        CourseDetailResponse courseDetailResponse = new CourseDetailResponse();
        courseDetailResponse.setId(courseInf.getId());
        courseDetailResponse.setName(courseInf.getName());
        courseDetailResponse.setStartDate(courseInf.getStartDate());
        courseDetailResponse.setEndDate(courseInf.getEndDate());
        courseDetailResponse.setTuition(courseInf.getTuition());

        List<TeacherInf> listTeachersResponse = new ArrayList<>();
        List<TeacherCourse> listTeachers = teacherCourseRepository.findByCourseIdAndIsDeleted(courseDetailResponse.getId(), Constants.NOT_DELETED);
        List<Long> listTeachersId = new ArrayList<>();
        for (TeacherCourse teacher: listTeachers) {
            listTeachersId.add(teacher.getTeacherId());
        }
        List<TeacherResponse> listTeacherResponse = commonService.listTeachers(listTeachersId);
        for (TeacherResponse teacherResponse: listTeacherResponse) {
            TeacherInf teacherInf = new TeacherInf(teacherResponse.getId(),teacherResponse.getName());
            listTeachersResponse.add(teacherInf);
        }

        courseDetailResponse.setListTeachers(listTeachersResponse);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseDetailResponse);

    }

    @Override
    public  ServiceResponse<List<CourseReponse>> allCourse() {
        List<CourseReponse> courseResponseList = new ArrayList<>();
        List<Course> listCourses = courseRepository.findAllByIsDeleted(Constants.NOT_DELETED);
        for (Course course: listCourses){
            CourseReponse courseReponse = new CourseReponse(course);
            courseResponseList.add(courseReponse);
        }
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseResponseList);
    }

    @Override
    public  ServiceResponse<List<StudentInf>> listStudents(Long courseId) {
        List<StudentList> studentList = studentListRepository.findByCourseIdAndIsDeleted(courseId, Constants.NOT_DELETED);
        List<StudentInf> studentInfList = new ArrayList<>();
        List<Long> studentsId = new ArrayList<>();
        for (StudentList student: studentList) {
            studentsId.add(student.getStudentId());
        }
        List<StudentReponse> studentReponseList = commonService.listStudents(studentsId);
        for (StudentReponse student: studentReponseList) {
            StudentInf studentInf = new StudentInf();
            studentInf.setId(student.getId());
            studentInf.setName(student.getName());
            studentInf.setEmail(student.getEmail());
            studentInf.setDob(student.getDob());
            studentInfList.add(studentInf);
        }
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, studentInfList);

    }

    @Override
    public ServiceResponse<CourseReponse> delete(Long id) {
        Optional<Course> findCourse = courseRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findCourse.isEmpty()) {
            return new ServiceResponse(MessageCode.COURSE_NOT_EXISTED, MessageCode.COURSE_NOT_EXISTED_MESSAGE, null);
        }
        Course courseInf = findCourse.get();
        courseInf.setIsDeleted(Constants.IS_DELETED);
        List<StudentList> studentList = studentListRepository.findByCourseIdAndIsDeleted(id, Constants.NOT_DELETED);
        for (StudentList student: studentList) {
            student.setIsDeleted(Constants.IS_DELETED);
        }
        studentListRepository.saveAll(studentList);
        List<TeacherCourse> listTeachers = teacherCourseRepository.findByCourseIdAndIsDeleted(id, Constants.NOT_DELETED);
        for (TeacherCourse teacher: listTeachers) {
            teacher.setIsDeleted(Constants.IS_DELETED);
        }
        teacherCourseRepository.saveAll(listTeachers);

        CourseReponse courseReponse = new CourseReponse(courseInf);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, courseReponse);
    }

    @Override
    public ServiceResponse<Boolean> addStudentsToCourse(Long courseId, List<Long> studentsId) {
        List<StudentList> listStudents = new ArrayList<>();
        for (Long studentId: studentsId) {
            Optional<StudentList> student = studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(courseId, studentId, Constants.IS_DELETED);
            if(student.isPresent()) {
                student.get().setIsDeleted(Constants.NOT_DELETED);
                listStudents.add(student.get());
            }
            else {
                StudentList studentList = new StudentList();
                studentList.setCourseId(courseId);
                studentList.setStudentId(studentId);
                studentList.setTuitionStatus(Constants.UNPAID_STATUS);
                studentList.setCreatedDate(new Date());
                studentList.setUpdatedDate(new Date());
                studentList.setIsDeleted(Constants.NOT_DELETED);
                listStudents.add(studentList);
            }
        }
        studentListRepository.saveAll(listStudents);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);
    }

    @Override
    public ServiceResponse<Boolean> removeStudentsFromCourse(Long courseId, List<Long> studentsId) {
        List<StudentList> listStudents = new ArrayList<>();
        for (Long studentId: studentsId) {
            Optional<StudentList> findStudentList = studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(courseId, studentId, Constants.NOT_DELETED);
            if (findStudentList.isEmpty()) {
                return new ServiceResponse(MessageCode.STUDENT_NOT_EXIST, MessageCode.STUDENT_NOT_EXIST_MESSAGE, false);
            }
            StudentList studentList = findStudentList.get();
            studentList.setUpdatedDate(new Date());
            studentList.setIsDeleted(Constants.IS_DELETED);
            listStudents.add(studentList);
        }
        studentListRepository.saveAll(listStudents);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);
    }

    @Override
    public ServiceResponse<Boolean> updateTuitionStatus(Long courseId, List<TuitionStatus> listTuitionStatus) {

        List<StudentList> listStudents = new ArrayList<>();
        for (TuitionStatus tuitionStatus: listTuitionStatus) {
            Optional<StudentList> findStudentList = studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(courseId, tuitionStatus.getIdStudent(), Constants.NOT_DELETED);
            if (findStudentList.isEmpty()) {
                return new ServiceResponse(MessageCode.STUDENT_NOT_EXIST, MessageCode.STUDENT_NOT_EXIST_MESSAGE, false);
            }
            StudentList studentList = findStudentList.get();
            studentList.setUpdatedDate(new Date());
            studentList.setTuitionStatus(tuitionStatus.getTuitionStatus());
            listStudents.add(studentList);
        }
        studentListRepository.saveAll(listStudents);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, true);
    }
}
