package com.example.demo.dto;

import jakarta.validation.constraints.*;
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
}
