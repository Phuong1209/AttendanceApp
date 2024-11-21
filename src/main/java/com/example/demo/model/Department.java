package com.example.demo.model;
import com.example.demo.dto.DepartmentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
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
    @JsonManagedReference("department-jobtype")
    private Set<JobType> jobTypes;

    @ManyToMany(mappedBy = "departments", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> users;

    //constructor
    public Department(DepartmentDTO departmentDTO){
        this.name = departmentDTO.getName();
    }

}