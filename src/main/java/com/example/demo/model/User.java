package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userFullName;
    private String userPasswords;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name="user_position",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Set<Position> positions;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name="user_department",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Set<Department> departments;

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Set<WorkingTime> workingTimes;

}