package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobtype")
@Getter
@Setter

public class JobType implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "jobTypes")
    @JsonBackReference
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobType",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> tasks;

}
