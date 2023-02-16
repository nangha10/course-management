package com.example.teacherservice.service.implement;

import com.example.teacherservice.dto.Constants;
import com.example.teacherservice.dto.MessageCode;
import com.example.teacherservice.dto.ServiceResponse;
import com.example.teacherservice.dto.request.CreateTeacherRequest;
import com.example.teacherservice.dto.response.TeacherResponse;
import com.example.teacherservice.entity.Teacher;
import com.example.teacherservice.repository.TeacherRepository;
import com.example.teacherservice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public ServiceResponse<TeacherResponse> create(CreateTeacherRequest createTeacherRequest) {
        Teacher teacher = new Teacher();
        teacher.setId(createTeacherRequest.getId());
        teacher.setName(createTeacherRequest.getName());
        teacher.setDob(createTeacherRequest.getDob());
        teacher.setEmail(createTeacherRequest.getEmail());
        teacher.setPhone(createTeacherRequest.getPhone());
        teacher.setExperience(createTeacherRequest.getExperience());
        teacher.setCreatedDate(new Date());
        teacher.setUpdatedDate(new Date());
        teacher.setIsDeleted(Constants.NOT_DELETED);
        Teacher teacherSaved = teacherRepository.save(teacher);
        TeacherResponse teacherResponse = new TeacherResponse(teacherSaved);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, teacherResponse);
    }

    @Override
    public ServiceResponse<TeacherResponse> update(CreateTeacherRequest createTeacherRequest) {
        Optional<Teacher> findTeacher = teacherRepository.findByIdAndIsDeleted(createTeacherRequest.getId(), Constants.NOT_DELETED);
        if (findTeacher.isEmpty()) {
            return new ServiceResponse(MessageCode.TEACHER_NOT_EXISTED, MessageCode.TEACHER_NOT_EXISTED_MESSAGE, null);
        }
        Teacher teacher = findTeacher.get();
        teacher.setName(createTeacherRequest.getName());
        teacher.setDob(createTeacherRequest.getDob());
        teacher.setEmail(createTeacherRequest.getEmail());
        teacher.setPhone(createTeacherRequest.getPhone());
        teacher.setExperience(createTeacherRequest.getExperience());
        teacher.setUpdatedDate(new Date());
        teacher.setIsDeleted(Constants.NOT_DELETED);
        teacherRepository.save(teacher);
        TeacherResponse teacherResponse = new TeacherResponse(teacher);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, teacherResponse);    }

    @Override
    public ServiceResponse<TeacherResponse> detail(Long id) {
        Optional<Teacher> findTeacher = teacherRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findTeacher.isEmpty()) {
            return new ServiceResponse(MessageCode.TEACHER_NOT_EXISTED, MessageCode.TEACHER_NOT_EXISTED_MESSAGE, null);
        }
        Teacher teacher = findTeacher.get();
        TeacherResponse teacherResponse = new TeacherResponse(teacher);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, teacherResponse);
    }

    @Override
    public ServiceResponse<TeacherResponse> delete(Long id) {
        Optional<Teacher> findTeacher = teacherRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
        if (findTeacher.isEmpty()) {
            return new ServiceResponse(MessageCode.TEACHER_NOT_EXISTED, MessageCode.TEACHER_NOT_EXISTED_MESSAGE, null);
        }
        Teacher teacher = findTeacher.get();
        teacher.setUpdatedDate(new Date());
        teacher.setIsDeleted(Constants.IS_DELETED);
        teacherRepository.save(teacher);
        TeacherResponse teacherResponse = new TeacherResponse(teacher);
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, teacherResponse);
    }

    @Override
    public ServiceResponse<List<TeacherResponse>> listTeachers() {
        List<Teacher> listTeachers = teacherRepository.findAllByIsDeleted(Constants.NOT_DELETED);
        List<TeacherResponse> response = new ArrayList<>();
        for (Teacher teacher : listTeachers) {
            response.add(new TeacherResponse(teacher));
        }
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);
    }

    @Override
    public ServiceResponse<List<TeacherResponse>> findListTeachers(List<Long> listId) {
        List<Teacher> listTeachers = teacherRepository.findByIdInAndIsDeleted(listId, Constants.NOT_DELETED);
        List<TeacherResponse> response = new ArrayList<>();
        for (Teacher teacher : listTeachers) {
            response.add(new TeacherResponse(teacher));
        }
        return new ServiceResponse(MessageCode.SUCCESSFUL, MessageCode.SUCCESSFUL_MESSAGE, response);
    }
}
