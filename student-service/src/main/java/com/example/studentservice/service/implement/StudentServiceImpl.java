package com.example.studentservice.service.implement;

import com.example.studentservice.dto.Constants;
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

    public String create(CreateStudentRequest createStudentRequest) {
        Optional<Student> findStudent = studentRepository.findByIdAndIsDeleted(createStudentRequest.getId(), Constants.NOT_DELETED);
        if (findStudent.isPresent()) {
            return ("Mã học sinh đã tồn tại");
        }
        Student student = new Student();
        student.setId(createStudentRequest.getId());
        student.setName(createStudentRequest.getName());
        student.setDob(createStudentRequest.getDob());
        student.setEmail(createStudentRequest.getEmail());
        student.setPhone(createStudentRequest.getPhone());
        student.setGrade(createStudentRequest.getGrade());
        student.setSchool(createStudentRequest.getSchool());
        student.setCreatedDate(new Date());
        student.setUpdatedDate(new Date());
        student.setIsDeleted(Constants.NOT_DELETED);
        studentRepository.save(student);
        return ("Them moi hoc vien thanh cong");
    }

    @Override
    public String update(CreateStudentRequest createStudentRequest) {
        Optional<Student> findStudent = studentRepository.findById(createStudentRequest.getId());
        if (findStudent.isEmpty()) {
            return ("Không tồn tại");
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
        return ("Sua thong tin hoc vien thanh cong");
    }

    @Override
    public StudentResponse detail(Long id) {
        Optional<Student> findStudent = studentRepository.findById(id);
        if (findStudent.isEmpty()) {
            return null;
        }
        Student student = findStudent.get();
        return new StudentResponse(student);
    }

    @Override
    public String delete(Long id) {
        Optional<Student> findStudent = studentRepository.findById(id);
        if (findStudent.isEmpty()) {
            return ("Không tồn tại");
        }
        Student student = findStudent.get();
        student.setUpdatedDate(new Date());
        student.setIsDeleted(Constants.IS_DELETED);
        studentRepository.save(student);
        return ("Xoa hoc vien thanh cong");
    }

    @Override
    public List<StudentResponse> listStudents() {
        List<Student> listStudents = studentRepository.findAllByIsDeleted(Constants.NOT_DELETED);
        List<StudentResponse> response = new ArrayList<>();
        for (Student student : listStudents) {
            response.add(new StudentResponse(student));
        }
        return response;
    }
}
