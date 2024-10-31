package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobtype")
public class JobType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String jobTypeName;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    @ManyToMany(mappedBy = "jobtypes")
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobtype",cascade = CascadeType.ALL)
    private Set<Task> tasks;
}