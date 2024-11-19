package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class UserDTO {
    private Long id;
    private String userName;
    private String fullName;
    private String password;
    private Set<DepartmentDTO> departments;
    private Set<WorkTimeDTO> workTimes;
}