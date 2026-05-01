package com.eduresult.controller;

import com.eduresult.model.Result;
import com.eduresult.model.Student;
import com.eduresult.repository.StudentRepository;
import com.eduresult.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<List<Result>> getResultsByAdmin(@PathVariable Long studentId) {
        return ResponseEntity.ok(resultService.getStudentResults(studentId));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<Result>> getMyResults() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByEmail(username) // Assuming email is username
                .orElseThrow(() -> new RuntimeException("Student profile not found"));
        return ResponseEntity.ok(resultService.getStudentResults(student.getId()));
    }
}
