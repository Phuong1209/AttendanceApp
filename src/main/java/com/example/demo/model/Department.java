package com.example.demo.model;
import com.example.demo.model.dto.DepartmentDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="department")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "department_jobtype",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "jobtype_id")
    )
    @JsonManagedReference
    private Set<JobType> jobTypes;

    @ManyToMany(mappedBy = "departments", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("departments")
    private Set<User> users;

    //constructor
    public Department(DepartmentDTO departmentDTO){
        this.name = departmentDTO.getName();
    }

    //setter and getter
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

    public Set<JobType> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(Set<JobType> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}