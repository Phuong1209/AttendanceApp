package com.example.demo.dto;

import lombok.Builder;
import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@Getter
@Setter
@Builder
//Hide all null field
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO {
    private Long id;
    private String userName;
    private String fullName;
    private String password;
    private Set<DepartmentDTO> departments;
    private Set<PositionDTO> positions;
    private Set<WorkTimeDTO> workTimes;

    //constructor (to map with department)
    public UserDTO() {
    }

    public UserDTO(Long id, String userName, String fullName) {
        this.userName = userName;
        this.id = id;
        this.fullName = fullName;
    }

    public UserDTO(Long id, String userName, String fullName, String password, Set<DepartmentDTO> departments, Set<PositionDTO> positions, Set<WorkTimeDTO> workTimes) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.departments = departments;
        this.positions = positions;
        this.workTimes = workTimes;
    }
}
