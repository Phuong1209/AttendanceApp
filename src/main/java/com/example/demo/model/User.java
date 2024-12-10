package com.example.demo.model;

import com.example.demo.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

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
//    @NotEmpty(message = "fullName should not be empty")
    private String fullName;
//    @NotEmpty(message = "userName should not be empty")
    private String userName;
    private String password;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name="user_position",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    @JsonManagedReference
//    @NotEmpty(message = "positions should not be empty")
    private Set<Position> positions;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_department",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @JsonManagedReference
//    @NotEmpty (message = "departments should not be empty")
    private Set<Department> departments;

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<WorkTime> workTimes;

    //constructor
    public User(UserDTO userDTO) {
        this.userName = userDTO.getUserName();
        this.fullName = userDTO.getFullName();
        this.password = userDTO.getPassword();
    }
}
