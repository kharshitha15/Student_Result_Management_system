package com.eduresult.service;

import com.eduresult.dto.MarksDTO;
import com.eduresult.model.Marks;
import com.eduresult.model.Student;
import com.eduresult.model.Subject;
import com.eduresult.repository.MarksRepository;
import com.eduresult.repository.StudentRepository;
import com.eduresult.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ResultService resultService;

    @Transactional
    public Marks saveMarks(MarksDTO marksDTO) {
        Student student = studentRepository.findById(marksDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(marksDTO.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Marks marks = marksRepository.findByStudentIdAndSubjectId(marksDTO.getStudentId(), marksDTO.getSubjectId())
                .orElse(Marks.builder()
                        .student(student)
                        .subject(subject)
                        .semester(marksDTO.getSemester())
                        .build());

        marks.setMarksObtained(marksDTO.getMarksObtained());
        Marks savedMarks = marksRepository.save(marks);

        // Trigger result calculation
        resultService.calculateAndSaveResult(student.getId(), marksDTO.getSemester());

        return savedMarks;
    }
}
