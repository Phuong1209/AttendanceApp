package com.example.demo.model.dto;

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
    //private Set<PositionDto> positions;
    private Set<DepartmentDTO> departments;
    private Set<WorkTimeDTO> workTimes;

    //constructor
    public UserDTO(Long id, String fullName, String userName, String password) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }

    public UserDTO() {
    }
}