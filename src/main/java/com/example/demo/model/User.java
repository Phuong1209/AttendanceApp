package com.example.demo.model;

import com.example.demo.dto.UserDTO;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.PositionRepository;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userFullName;
    private String userPasswords;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_position",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    @JsonManagedReference
    private Set<Position> positions;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_department",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @JsonManagedReference
    private Set<Department> departments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<WorkingTime> workingTimes;

//    public void updateFromDTO(UserDTO userDTO) {
//        this.userName = userDTO.getUserName();
//        this.userFullName = userDTO.getUserFullName();
//        this.positions = userDTO.getPositions();
//        this.departments = userDTO.getDepartments();
//    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.userName = userDTO.getUserName();
        this.userFullName = userDTO.getUserFullName();
    }
}