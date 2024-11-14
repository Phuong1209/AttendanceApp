package com.example.demo.model.dto;

import java.util.Set;

public class JobTypeDto {
    private Long id;
    private String name;
    private Set<DepartmentDTO> departments;
    //private Set<TaskDto> tasks;

    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<DepartmentDTO> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<DepartmentDTO> departments) {
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

/*    public Set<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskDto> tasks) {
        this.tasks = tasks;
    }*/
}