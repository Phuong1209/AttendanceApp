package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class ProjectDTO {
    private Long id;
    private String name;
    private String code;
    private Set<TaskDTO> tasks;
}
