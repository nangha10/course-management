package com.example.courseservice.repository;

import com.example.courseservice.entity.StudentList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentListRepository extends JpaRepository<StudentList, Long> {
    List<StudentList> findByCourseIdAndIsDeleted(Long courseId, int isDeleted);
    Optional<StudentList> findByCourseIdAndStudentIdAndIsDeleted(Long courseId, Long studentId, int isDeleted);


}
