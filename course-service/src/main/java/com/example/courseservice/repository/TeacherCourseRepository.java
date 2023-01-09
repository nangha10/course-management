package com.example.courseservice.repository;

import com.example.courseservice.entity.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
    Optional<TeacherCourse> findByTeacherIdAndCourseId(Long teacherId, Long courseId);

    List<TeacherCourse> findByCourseIdAndIsDeleted(Long courseId, int isDeleted);
}
