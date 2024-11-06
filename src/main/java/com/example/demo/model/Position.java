package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "position")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String positionName;
    @ManyToMany(mappedBy="positions", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<User> users;


}