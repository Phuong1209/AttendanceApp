package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@Getter
@Setter
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
}
