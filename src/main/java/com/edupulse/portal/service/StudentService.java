package com.edupulse.portal.service;

import com.edupulse.portal.model.Student;
import com.edupulse.portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student login(String rollNumber, String password) {
        try {
            Student student = repository.findByRollNumber(rollNumber);
            if (student != null && student.getPassword().equals(password)) {
                return student;
            }
        } catch (Exception e) {
            // Handle not found
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void addStudent(Student student) {
        repository.save(student);
    }

    public void deleteStudent(String rollNumber) {
        repository.delete(rollNumber);
    }
}
