package com.eduresult.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private String branch;
    private Integer currentSemester;
    private String username;
    private String password;
}
