package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobtype")

public class JobType implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "jobTypes")
    //@JsonBackReference("department-jobtype")
    @JsonIgnore
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobType",cascade = CascadeType.ALL)
    //@JsonManagedReference("jobtype-task")
    @JsonIgnore
    private Set<Task> tasks;

}
