package com.eduresult.repository;

import com.eduresult.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentId(Long studentId);
    Optional<Result> findByStudentIdAndSemester(Long studentId, Integer semester);
}
