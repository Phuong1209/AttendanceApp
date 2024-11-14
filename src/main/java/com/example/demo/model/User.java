package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

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
    private String fullName;
    private String userName;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Position> positions;

    @ManyToMany
    @JoinTable(
            name="user_department",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @JsonIgnoreProperties("users")
    private Set<Department> departments;

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL)
    private Set<WorkTime> workTimes;

    //setter and getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwords) {
        this.password = passwords;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<WorkTime> getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(Set<WorkTime> workTimes) {
        this.workTimes = workTimes;
    }
}
