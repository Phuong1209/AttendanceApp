package com.example.demo.model.dto;

import com.example.demo.model.Department;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public class JobTypeDto {
    @NotEmpty(message = "The name is required")
    private String name;
    private Set<Department> departments;

    //getter and setter
    public @NotEmpty(message = "The name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The nuserame is required") String name) {
        this.name = name;
    }

    public Set<Department> getDepartment() {
        return departments;
    }

    public void setDepartment(Set<Department> departments) {
        this.departments = departments;
    }
}
