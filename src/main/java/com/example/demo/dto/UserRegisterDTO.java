package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRegisterDTO {
    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String fullName;
    @NotNull
    private Set<DepartmentDTO> departments;
    @NotNull
    private Set<PositionDTO> positions;
}
