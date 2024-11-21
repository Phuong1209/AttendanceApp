package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class UserDTO {
    private Long id;
    private String fullName;
    private String userName;
    private String password;
    private Set<DepartmentDTO> departments;
    private Set<WorkTimeDTO> workTimes;
    private Set<PositionDTO> positions;

}
