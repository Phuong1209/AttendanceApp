package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
//@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobtype")
@Getter
@Setter

public class JobType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "jobTypes")
    @JsonBackReference
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobType",cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Task> tasks;

}
