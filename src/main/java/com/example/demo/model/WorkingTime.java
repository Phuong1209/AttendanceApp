package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "working_time")

public class WorkingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalDateTime checkin_time;
    private LocalDateTime checkout_time;
    private Float breaktime;
    private Float worktime;
    private Float overtime;

    @OneToMany(mappedBy = "workingTime", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonBackReference
    private User user;
}


