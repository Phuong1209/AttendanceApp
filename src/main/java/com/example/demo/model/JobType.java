package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    private String jobTypeName;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    @ManyToMany(mappedBy = "jobtypes")
    @JsonBackReference
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobtype",cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Task> tasks;
}
