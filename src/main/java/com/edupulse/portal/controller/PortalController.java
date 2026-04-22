package com.edupulse.portal.controller;

import com.edupulse.portal.model.Student;
import com.edupulse.portal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PortalController {

    @Autowired
    private StudentService service;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String rollNumber = credentials.get("rollNumber");
        String password = credentials.get("password");
        Student student = service.login(rollNumber, password);
        
        if (student != null) {
            return Map.of("status", "success", "user", student);
        }
        return Map.of("status", "error", "message", "Invalid credentials");
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @PostMapping("/students")
    public void addStudent(@RequestBody Student student) {
        service.addStudent(student);
    }

    @DeleteMapping("/students/{rollNumber}")
    public void deleteStudent(@PathVariable String rollNumber) {
        service.deleteStudent(rollNumber);
    }

    @GetMapping("/export")
    public String exportToHtml() {
        List<Student> students = service.getAllStudents();
        StringBuilder html = new StringBuilder();
        html.append("<html><body><table border='1'><thead><tr>")
            .append("<th>Roll No</th><th>Name</th><th>Physics</th><th>Chemistry</th><th>Math</th><th>Total</th>")
            .append("</tr></thead><tbody>");
        
        for (Student s : students) {
            html.append("<tr>")
                .append("<td>").append(s.getRollNumber()).append("</td>")
                .append("<td>").append(s.getName()).append("</td>")
                .append("<td>").append(s.getPhysics()).append("</td>")
                .append("<td>").append(s.getChemistry()).append("</td>")
                .append("<td>").append(s.getMath()).append("</td>")
                .append("<td>").append(s.getTotal()).append("</td>")
                .append("</tr>");
        }
        
        html.append("</tbody></table></body></html>");
        return html.toString();
    }
}
