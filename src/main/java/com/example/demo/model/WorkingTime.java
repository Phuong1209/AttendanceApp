package com.example.demo.model;
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
//    private Long user_id;
    private LocalDate date;
    private LocalDateTime checkin_time;
    private LocalDateTime checkout_time;
    private LocalDateTime breaktime;
    private LocalDateTime worktime;
    private LocalDateTime overtime;

    @OneToMany(mappedBy = "workingTime", cascade = CascadeType.ALL)
    private Set<Task> tasks;

//    @ManyToOne
//    @JoinColumn(name = "user_id", insertable = false, updatable = false)
//    private User user;
    // In WorkingTime class
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    private Long user_id; // This should be Long if User's id is Long


}