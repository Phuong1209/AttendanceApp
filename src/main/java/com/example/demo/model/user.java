package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String userFullname;
    private String userPassword;
    @ManyToMany
    private Set<position> position;
    @ManyToMany
    private Set<department> department;
}
