package com.example.courseservice.repository;

import com.example.courseservice.entity.RollCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RollCallRepository extends JpaRepository<RollCall, Long> {
    Optional<RollCall> findByLessonIdAndStudentIdAndIsDeleted(Long lesssonId, Long studentId, int isDeleted);
    List<RollCall> findByLessonIdAndIsDeleted(Long lesssonId, int isDeleted);

}
