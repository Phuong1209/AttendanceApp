package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "working_time")

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorkingTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalDateTime checkin_time;
    private LocalDateTime checkout_time;
    private Float breaktime;
    private Float worktime;
    private Float overtime;

    @OneToMany(mappedBy = "workingTime", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonBackReference
    private User user;
}


