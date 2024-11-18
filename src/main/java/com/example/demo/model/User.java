package com.example.demo.model;

import com.example.demo.model.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String userName;
    private String password;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @JsonBackReference
    private Position position;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name="user_department",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @JsonManagedReference
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
