package com.example.demo.model.dto;

import com.example.demo.model.Department;
import jakarta.validation.constraints.NotEmpty;

public class UserDto {
    @NotEmpty(message = "The nuserame is required")
    private String username;
    @NotEmpty(message = "The fullname is required")
    private String fullname;
    @NotEmpty(message = "The password is required")
    private String password;
    /*private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
