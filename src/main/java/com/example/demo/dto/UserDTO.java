package com.example.demo.dto;

import jdk.jshell.Snippet;
import lombok.Builder;
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
    private Set<PositionDTO> positions;
    private Set<WorkTimeDTO> workTimes;

}





