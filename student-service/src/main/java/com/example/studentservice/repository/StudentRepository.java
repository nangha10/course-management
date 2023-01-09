package com.example.studentservice.repository;

import com.example.studentservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByIsDeleted(int isDeleted);
    Optional<Student> findByIdAndIsDeleted(Long id, int isDeleted);
}
