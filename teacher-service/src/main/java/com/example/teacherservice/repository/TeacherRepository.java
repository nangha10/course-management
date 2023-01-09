package com.example.teacherservice.repository;


import com.example.teacherservice.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByIsDeleted(int isDeleted);
    Optional<Teacher> findByIdAndIsDeleted(Long id, int isDeleted);
}
