package com.example.studentservice.service.implement;

import com.example.studentservice.dto.Constants;
import com.example.studentservice.dto.MessageCode;
import com.example.studentservice.dto.ServiceResponse;
import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.dto.request.CreateStudentRequest;
import com.example.studentservice.dto.response.StudentResponse;
import com.example.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public ServiceResponse<StudentResponse> create(CreateStudentRequest createStudentRequest) {

        Student student = new Student();
        student.setName(createStudentRequest.getName());
        student.setDob(createStudentRequest.getDob());
        student.setEmail(createStudentRequest.getEmail());
        student.setPhone(createStudentRequest.getPhone());
        student.setGrade(createStudentRequest.getGrade());
        student.setSchool(createStudentRequest.getSchool());
        student.setCreatedDate(new Date());
        student.setUpdatedDate(new Date());
        student.setIsDeleted(Constants.NOT_DELETED);
        Student studentSaved = studentRepository.save(student);
        StudentResponse studentResponse = new StudentResponse(studentSaved);

        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, studentResponse);
    }

    @Override
    public ServiceResponse<StudentResponse> update(CreateStudentRequest createStudentRequest) {
        Optional<Student> findStudent = studentRepository.findByIdAndIsDeleted(createStudentRequest.getId(), Constants.NOT_DELETED);
        if (findStudent.isEmpty()) {
            return new ServiceResponse(MessageCode.STUDENT_NOT_EXISTED, MessageCode.STUDENT_NOT_EXISTED_MESSAGE, null);
        }
        Student student = findStudent.get();
        student.setName(createStudentRequest.getName());
        student.setDob(createStudentRequest.getDob());
        student.setEmail(createStudentRequest.getEmail());
        student.setPhone(createStudentRequest.getPhone());
        student.setGrade(createStudentRequest.getGrade());
        student.setSchool(createStudentRequest.getSchool());
        student.setUpdatedDate(new Date());
        student.setIsDeleted(Constants.NOT_DELETED);
        studentRepository.save(student);

        Student studentSaved = studentRepository.save(student);
        StudentResponse studentResponse = new StudentResponse(studentSaved);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, studentResponse);
    }

    @Override
    public ServiceResponse<StudentResponse> detail(Long id) {
        Optional<Student> findStudent = studentRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findStudent.isEmpty()) {
            return new ServiceResponse(MessageCode.STUDENT_NOT_EXISTED, MessageCode.STUDENT_NOT_EXISTED_MESSAGE, null);
        }
        Student student = findStudent.get();
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, new StudentResponse(student));

    }

    @Override
    public ServiceResponse<StudentResponse> delete(Long id) {
        Optional<Student> findStudent = studentRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findStudent.isEmpty()) {
            return new ServiceResponse(MessageCode.STUDENT_NOT_EXISTED, MessageCode.STUDENT_NOT_EXISTED_MESSAGE, null);
        }
        Student student = findStudent.get();
        student.setUpdatedDate(new Date());
        student.setIsDeleted(Constants.IS_DELETED);
        studentRepository.save(student);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, new StudentResponse(student));
    }

    @Override
    public ServiceResponse<List<StudentResponse>> listStudents() {
        List<Student> listStudents = studentRepository.findAllByIsDeleted(Constants.NOT_DELETED);
        List<StudentResponse> response = new ArrayList<>();
        for (Student student : listStudents) {
            response.add(new StudentResponse(student));
        }
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);
    }

    @Override
    public ServiceResponse<List<StudentResponse>> findListStudents(List<Long> listId) {
        List<Student> listStudents = studentRepository.findByIdInAndIsDeleted(listId, Constants.NOT_DELETED);
        List<StudentResponse> response = new ArrayList<>();
        for (Student student : listStudents) {
            response.add(new StudentResponse(student));
        }
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);
    }
}
