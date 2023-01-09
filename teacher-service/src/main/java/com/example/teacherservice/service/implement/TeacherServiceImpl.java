package com.example.teacherservice.service.implement;

import com.example.teacherservice.dto.Constants;
import com.example.teacherservice.dto.request.CreateTeacherRequest;
import com.example.teacherservice.dto.response.TeacherResponse;
import com.example.teacherservice.entity.Teacher;
import com.example.teacherservice.repository.TeacherRepository;
import com.example.teacherservice.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    private TeacherRepository teacherRepository;

    public String create(CreateTeacherRequest createTeacherRequest) {
        Optional<Teacher> findTeacher = teacherRepository.findByIdAndIsDeleted(createTeacherRequest.getId(), Constants.NOT_DELETED);
        if (findTeacher.isPresent()) {
            return ("Mã giáo viên đã tồn tại");
        }        Teacher teacher = new Teacher();
        teacher.setId(createTeacherRequest.getId());
        teacher.setName(createTeacherRequest.getName());
        teacher.setDob(createTeacherRequest.getDob());
        teacher.setEmail(createTeacherRequest.getEmail());
        teacher.setPhone(createTeacherRequest.getPhone());
        teacher.setExperience(createTeacherRequest.getExperience());
        teacher.setCreatedDate(new Date());
        teacher.setUpdatedDate(new Date());
        teacher.setIsDeleted(Constants.NOT_DELETED);
        teacherRepository.save(teacher);
        return ("Them moi giao vien thanh cong");
    }

    @Override
    public String update(CreateTeacherRequest createTeacherRequest) {
        Teacher teacher = teacherRepository.findById(createTeacherRequest.getId()).get();
        teacher.setName(createTeacherRequest.getName());
        teacher.setDob(createTeacherRequest.getDob());
        teacher.setEmail(createTeacherRequest.getEmail());
        teacher.setPhone(createTeacherRequest.getPhone());
        teacher.setExperience(createTeacherRequest.getExperience());
        teacher.setUpdatedDate(new Date());
        teacher.setIsDeleted(Constants.NOT_DELETED);
        teacherRepository.save(teacher);
        return ("Sua thong tin giao vien thanh cong");
    }

    @Override
    public TeacherResponse detail(Long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        return new TeacherResponse(teacher);
    }

    @Override
    public String delete(Long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        teacher.setUpdatedDate(new Date());
        teacher.setIsDeleted(Constants.IS_DELETED);
        teacherRepository.save(teacher);
        ;
        return ("Xoa giao vien thanh cong");
    }

    @Override
    public List<TeacherResponse> listTeachers() {
        List<Teacher> listStudents = teacherRepository.findAllByIsDeleted(Constants.NOT_DELETED);
        List<TeacherResponse> response = new ArrayList<>();
        for (Teacher teacher : listStudents) {
            response.add(new TeacherResponse(teacher));
        }
        return response;
    }
}
