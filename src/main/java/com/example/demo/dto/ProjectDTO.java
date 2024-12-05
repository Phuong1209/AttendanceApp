package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder

public class ProjectDTO {
    private Long id;
    private String name;
    private String code;
    private Set<TaskDTO> tasks;

    //constructor
    public ProjectDTO() {
    }
    public ProjectDTO(Long id, String name, String code, Set<TaskDTO> tasks) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.tasks = tasks;
    }
}
