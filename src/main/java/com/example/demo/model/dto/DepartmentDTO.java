package com.example.demo.model.dto;

import java.util.Set;

public class DepartmentDTO {
    private Long id;
    private String name;
   // private Set<JobTypeDto> jobTypes;
    private Set<UserDTO> users;

    //constructor
    public DepartmentDTO(Long id, String name, Set<UserDTO> users) {
        this.id = id;
        this.name = name;
    //    this.jobTypes = jobTypes;
        this.users = users;
    }

    public DepartmentDTO() {

    }

    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Set<JobTypeDto> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(Set<JobTypeDto> jobTypes) {
        this.jobTypes = jobTypes;
    }*/

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }
}



