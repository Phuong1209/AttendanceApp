package com.example.demo.dto;

import com.example.demo.model.Department;
import com.example.demo.model.Position;
import lombok.Data;

import java.util.Set;
@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String userFullName;
    private Set<PositionDTO> positions;
    private Set<DepartmentDTO> departments;
}
