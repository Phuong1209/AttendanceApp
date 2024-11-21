package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "work_time")
@Getter
@Setter

public class WorkTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private Float breakTime;
    private Float workTime;
    private Float overTime;

    @OneToMany(mappedBy = "workTime", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}