package com.example.demo.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class DepartmentDto {
    @NotEmpty(message = "The name is required")
    private String name;

    public @NotEmpty(message = "The name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name is required") String name) {
        this.name = name;
    }
}