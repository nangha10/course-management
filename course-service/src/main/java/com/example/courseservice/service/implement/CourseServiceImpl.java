package com.example.courseservice.service.implement;

import com.example.courseservice.dto.Constants;
import com.example.courseservice.dto.request.course.CourseCreateRequest;
import com.example.courseservice.dto.request.course.TuitionStatus;
import com.example.courseservice.dto.response.course.*;
import com.example.courseservice.entity.Course;
import com.example.courseservice.entity.Lesson;
import com.example.courseservice.entity.StudentList;
import com.example.courseservice.entity.TeacherCourse;
import com.example.courseservice.feignclients.StudentFeignClient;
import com.example.courseservice.feignclients.TeacherFeignClient;
import com.example.courseservice.repository.CourseRepository;
import com.example.courseservice.repository.StudentListRepository;
import com.example.courseservice.repository.TeacherCourseRepository;
import com.example.courseservice.service.CommonService;
import com.example.courseservice.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

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
    public String create(CourseCreateRequest courseCreateRequest) {
        Optional<Course> findCourse = courseRepository.findByIdAndIsDeleted(courseCreateRequest.getId(), Constants.NOT_DELETED);
        if (findCourse.isPresent()) {
            return ("Mã lớp học đã tồn tại");
        }
        Course course = new Course();
        course.setId(courseCreateRequest.getId());
        course.setName(courseCreateRequest.getName());
        course.setStartDate(courseCreateRequest.getStartDate());
        course.setEndDate(courseCreateRequest.getEndDate());
        course.setTuition(courseCreateRequest.getTuition());
        course.setCreatedDate(new Date());
        course.setUpdatedDate(new Date());
        course.setIsDeleted(Constants.NOT_DELETED);

        List<TeacherCourse> listTeachers = new ArrayList<>();
        for ( Long teacherId:courseCreateRequest.getListTeacherId()) {
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setCourseId(course.getId());
            teacherCourse.setTeacherId(teacherId);
            teacherCourse.setCreatedDate(new Date());
            teacherCourse.setUpdatedDate(new Date());
            teacherCourse.setIsDeleted(Constants.NOT_DELETED);
            listTeachers.add(teacherCourse);
        }
        courseRepository.save(course);
        teacherCourseRepository.saveAll(listTeachers);
        return ("Them moi khoa hoc thanh cong");
    }

    @Override
    public String update(CourseCreateRequest courseCreateRequest) {
        Course courseUpdate = courseRepository.findById(courseCreateRequest.getId()).get();
        courseUpdate.setName(courseCreateRequest.getName());
        courseUpdate.setStartDate(courseCreateRequest.getStartDate());
        courseUpdate.setEndDate(courseCreateRequest.getEndDate());
        courseUpdate.setUpdatedDate(new Date());
        List<TeacherCourse> listTeachers = new ArrayList<>();
        List<TeacherCourse> listTeachersNotUpdate = teacherCourseRepository.findByCourseIdAndIsDeleted(courseCreateRequest.getId(), Constants.NOT_DELETED);
        for (TeacherCourse teacher: listTeachersNotUpdate) {
            if(!Arrays.asList(listTeachersNotUpdate).contains(teacher)) {
                teacher.setIsDeleted(Constants.IS_DELETED);
                teacher.setUpdatedDate(new Date());
                listTeachers.add(teacher);
            }
        }
        for ( Long teacherId:courseCreateRequest.getListTeacherId()) {
            Optional<TeacherCourse> teacherCourse = teacherCourseRepository.findByTeacherIdAndCourseId(teacherId, courseUpdate.getId());
            if(teacherCourse.isEmpty()) {
                teacherCourse.get().setCourseId(courseUpdate.getId());
                teacherCourse.get().setTeacherId(teacherId);
                teacherCourse.get().setCreatedDate(new Date());
                teacherCourse.get().setUpdatedDate(new Date());
                teacherCourse.get().setIsDeleted(Constants.NOT_DELETED);
                listTeachers.add(teacherCourse.get());
            } else
                if(teacherCourse.get().getIsDeleted() == Constants.IS_DELETED) {
                    teacherCourse.get().setIsDeleted(Constants.NOT_DELETED);
                    teacherCourse.get().setUpdatedDate(new Date());
                    listTeachers.add(teacherCourse.get());
                }
        }
        courseRepository.save(courseUpdate);
        teacherCourseRepository.saveAll(listTeachers);
        return ("Chinh sua khoa hoc thanh cong");
    }

    @Override
    public CourseDetailResponse courseDetail(Long id) {
        Course courseInf = courseRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED).get();
        CourseDetailResponse courseDetailResponse = new CourseDetailResponse();
        courseDetailResponse.setId(courseInf.getId());
        courseDetailResponse.setName(courseInf.getName());
        courseDetailResponse.setStartDate(courseInf.getStartDate());
        courseDetailResponse.setEndDate(courseInf.getEndDate());
        courseDetailResponse.setTuition(courseInf.getTuition());
        List<TeacherInf> listTeachersResponse = new ArrayList<>();
        List<TeacherCourse> listTeachers = teacherCourseRepository.findByCourseIdAndIsDeleted(courseDetailResponse.getId(), Constants.NOT_DELETED);
        for (TeacherCourse teacher: listTeachers) {
            TeacherResponse teacherResponse = commonService.detailTeacher(teacher.getTeacherId());
            TeacherInf teacherInf = new TeacherInf(teacherResponse.getId(),teacherResponse.getName());
            listTeachersResponse.add(teacherInf);
        }
        courseDetailResponse.setListTeachers(listTeachersResponse);
        return courseDetailResponse;
    }

    @Override
    public List<CourseReponse> allCourse() {
        List<CourseReponse> courseReponseList = new ArrayList<>();
        List<Course> listCourses = courseRepository.findAllByIsDeleted(Constants.NOT_DELETED);
        for (Course course: listCourses){
            CourseReponse courseReponse = new CourseReponse();
            courseReponse.setId(course.getId());
            courseReponse.setName(course.getName());
            courseReponse.setStartDate(course.getStartDate());
            courseReponse.setEndDate(course.getEndDate());
            courseReponseList.add(courseReponse);
        }
        return courseReponseList;
    }

    @Override
    public List<StudentInf> listStudents(Long courseId) {
        List<StudentList> studentList= studentListRepository.findByCourseIdAndIsDeleted(courseId, Constants.NOT_DELETED);
        List<StudentInf> studentInfList = new ArrayList<>();
        for (StudentList student: studentList) {
            StudentReponse studentResponse = commonService.detailStudent(student.getStudentId());
            StudentInf studentInf = new StudentInf();
            studentInf.setId(student.getStudentId());
            studentInf.setName(studentResponse.getName());
            studentInf.setEmail(studentResponse.getEmail());
            studentInf.setDob(studentResponse.getDob());
            studentInf.setTuitionStatus(student.getTuitionStatus());
            studentInfList.add(studentInf);
        }
        return studentInfList;
    }

    @Override
    public String delete(Long id) {
        Course courseInf = courseRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED).get();
        courseInf.setIsDeleted(Constants.IS_DELETED);
        List<StudentList> studentList= studentListRepository.findByCourseIdAndIsDeleted(id, Constants.NOT_DELETED);
        for (StudentList student: studentList) {
            student.setIsDeleted(Constants.IS_DELETED);
        }
        studentListRepository.saveAll(studentList);
        List<TeacherCourse> listTeachers = teacherCourseRepository.findByCourseIdAndIsDeleted(id, Constants.NOT_DELETED);
        for (TeacherCourse teacher: listTeachers) {
            teacher.setIsDeleted(Constants.IS_DELETED);
        }
        teacherCourseRepository.saveAll(listTeachers);
        return ("Xoa khoa hoc thanh cong");
    }

    @Override
    public String addStudentsToCourse(Long courseId, List<Long> studentsId) {
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
        return ("Them hoc vien thanh cong");
    }

    @Override
    public String removeStudentsFromCourse(Long courseId, List<Long> studentsId) {
        List<StudentList> listStudents = new ArrayList<>();
        for (Long studentId: studentsId) {
            StudentList studentList = studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(courseId, studentId, Constants.NOT_DELETED).get();
            studentList.setUpdatedDate(new Date());
            studentList.setIsDeleted(Constants.IS_DELETED);
            listStudents.add(studentList);
        }
        studentListRepository.saveAll(listStudents);
        return ("Xoa hoc vien thanh cong");
    }

    @Override
    public String updateTuitionStatus(Long courseId, List<TuitionStatus> listTuitionStatus) {

        List<StudentList> listStudents = new ArrayList<>();
        for (TuitionStatus tuitionStatus: listTuitionStatus) {
            StudentList studentList = studentListRepository.findByCourseIdAndStudentIdAndIsDeleted(courseId, tuitionStatus.getIdStudent(), Constants.NOT_DELETED).get();
            studentList.setUpdatedDate(new Date());
            studentList.setTuitionStatus(tuitionStatus.getTuitionStatus());
            listStudents.add(studentList);
        }
        studentListRepository.saveAll(listStudents);
        return ("Xoa hoc vien thanh cong");
    }
    public TeacherResponse detailTeacher(Long id) {
        TeacherResponse teacherResponse = teacherFeignClient.detailTeacher(id);
        return teacherResponse;
    }


}
