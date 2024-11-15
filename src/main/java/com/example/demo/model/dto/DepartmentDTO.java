package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class DepartmentDTO {
    private Long id;
    private String name;
   // private Set<JobTypeDto> jobTypes;
    private Set<UserDTO> users;

    //constructor
/*    public DepartmentDTO(Long id, String name, Set<UserDTO> users) {
        this.id = id;
        this.name = name;
    //    this.jobTypes = jobTypes;
        this.users = users;
    }

    public DepartmentDTO() {
    }*/

}



