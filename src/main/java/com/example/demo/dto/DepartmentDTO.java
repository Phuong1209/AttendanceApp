package com.example.demo.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@Data
@Builder

public class DepartmentDTO {
    private Long id;
    private String name;
    private Set<UserDTO> users;
    private Set<JobTypeDTO> jobTypes;

    //constructor
    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String name, Set<UserDTO> users, Set<JobTypeDTO> jobTypes) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.jobTypes = jobTypes;
    }
}
