package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class JobTypeDto {
    private Long id;
    private String name;
    private Set<DepartmentDTO> departments;
    //private Set<TaskDto> tasks;

}