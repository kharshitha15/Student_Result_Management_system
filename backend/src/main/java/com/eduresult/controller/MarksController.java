package com.eduresult.controller;

import com.eduresult.dto.MarksDTO;
import com.eduresult.model.Marks;
import com.eduresult.service.MarksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY') or hasRole('ADMIN')")
    public ResponseEntity<Marks> enterMarks(@Valid @RequestBody MarksDTO marksDTO) {
        return ResponseEntity.ok(marksService.saveMarks(marksDTO));
    }
}
