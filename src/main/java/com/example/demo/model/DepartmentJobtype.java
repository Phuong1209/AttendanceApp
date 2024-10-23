package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="department_jobType")
public class DepartmentJobtype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long department_id;
    private Long jobType_id;
    @ManyToMany
    private Set<Jobtype> jobType;
    @ManyToMany
    private Set<Department> department;
}


