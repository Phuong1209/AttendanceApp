package com.example.demo.dto;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter


public class DepartmentDTO {
    private Long id;
    private String name;
    private Set<UserDTO> users;
    private Set<JobTypeDTO> jobTypes;


}
