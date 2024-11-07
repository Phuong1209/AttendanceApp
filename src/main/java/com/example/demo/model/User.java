package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @ManyToMany
    @JoinTable(
            name="user_position",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )

    private Set<Position> positions;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name="user_department",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "id")
    )

    private Set<Department> departments;

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL)
    private Set<WorkingTime> workingTimes;

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

    public Set<WorkingTime> getWorkingTimes() {
        return workingTimes;
    }

    public void setWorkingTimes(Set<WorkingTime> workingTimes) {
        this.workingTimes = workingTimes;
    }
}
