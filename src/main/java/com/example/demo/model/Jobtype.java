package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="jobtype")
public class Jobtype {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String jobType_name;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    @ManyToMany(mappedBy = "jobtypes")
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobtype",cascade = CascadeType.ALL)
    private Set<Task> tasks;


}
