package com.eduresult.service;

import com.eduresult.model.*;
import com.eduresult.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public void calculateAndSaveResult(Long studentId, Integer semester) {
        List<Marks> semesterMarks = marksRepository.findByStudentIdAndSemester(studentId, semester);
        
        if (semesterMarks.isEmpty()) return;

        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Marks mark : semesterMarks) {
            int gradePoint = getGradePoint(mark.getMarksObtained());
            int credits = mark.getSubject().getCredits();
            totalGradePoints += (gradePoint * credits);
            totalCredits += credits;
        }

        double sgpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;

        // Save or update current result first
        Result currentResult = resultRepository.findByStudentIdAndSemester(studentId, semester)
                .orElse(Result.builder()
                        .student(studentRepository.findById(studentId)
                                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId)))
                        .semester(semester)
                        .build());
        
        currentResult.setSgpa(sgpa);
        resultRepository.save(currentResult);

        // Calculate CGPA (Average of ALL SGPAs for the student)
        List<Result> allResults = resultRepository.findByStudentId(studentId);
        double sumSgpa = 0;
        for (Result res : allResults) {
            sumSgpa += res.getSgpa();
        }
        double cgpa = allResults.isEmpty() ? 0.0 : sumSgpa / allResults.size();

        // Update all student results with the new CGPA
        for (Result res : allResults) {
            res.setCgpa(cgpa);
            resultRepository.save(res);
        }
    }

    private int getGradePoint(int marks) {
        if (marks >= 90) return 10;
        if (marks >= 80) return 9;
        if (marks >= 70) return 8;
        if (marks >= 60) return 7;
        if (marks >= 50) return 6;
        if (marks >= 40) return 5;
        return 0;
    }

    public List<Result> getStudentResults(Long studentId) {
        return resultRepository.findByStudentId(studentId);
    }
}
