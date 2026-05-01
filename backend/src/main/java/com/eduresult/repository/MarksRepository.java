package com.eduresult.repository;

import com.eduresult.model.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MarksRepository extends JpaRepository<Marks, Long> {
    List<Marks> findByStudentId(Long studentId);
    List<Marks> findByStudentIdAndSemester(Long studentId, Integer semester);
    Optional<Marks> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
