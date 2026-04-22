package com.edupulse.portal.repository;

import com.edupulse.portal.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> {
        Student student = new Student();
        student.setRollNumber(rs.getString("roll_number"));
        student.setName(rs.getString("name"));
        student.setPassword(rs.getString("password"));
        student.setPhysics(rs.getInt("physics"));
        student.setChemistry(rs.getInt("chemistry"));
        student.setMath(rs.getInt("math"));
        return student;
    };

    public Student findByRollNumber(String rollNumber) {
        String sql = "SELECT * FROM students WHERE roll_number = ?";
        return jdbcTemplate.queryForObject(sql, studentRowMapper, rollNumber);
    }

    public List<Student> findAll() {
        String sql = "SELECT * FROM students";
        return jdbcTemplate.query(sql, studentRowMapper);
    }

    public int save(Student student) {
        String sql = "INSERT INTO students (roll_number, name, password, physics, chemistry, math) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, student.getRollNumber(), student.getName(), student.getPassword(), 
                                   student.getPhysics(), student.getChemistry(), student.getMath());
    }

    public int delete(String rollNumber) {
        String sql = "DELETE FROM students WHERE roll_number = ?";
        return jdbcTemplate.update(sql, rollNumber);
    }
}
