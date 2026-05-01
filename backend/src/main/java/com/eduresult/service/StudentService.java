package com.eduresult.service;

import com.eduresult.dto.StudentDTO;
import com.eduresult.model.Role;
import com.eduresult.model.Student;
import com.eduresult.model.User;
import com.eduresult.repository.StudentRepository;
import com.eduresult.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Student createStudent(StudentDTO studentDTO) {
        User user = User.builder()
                .username(studentDTO.getUsername())
                .password(encoder.encode(studentDTO.getPassword()))
                .role(Role.ROLE_STUDENT)
                .build();
        user = userRepository.save(user);

        Student student = Student.builder()
                .name(studentDTO.getName())
                .email(studentDTO.getEmail())
                .branch(studentDTO.getBranch())
                .currentSemester(studentDTO.getCurrentSemester())
                .user(user)
                .build();
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }
    
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
        userRepository.delete(student.getUser());
    }
}
