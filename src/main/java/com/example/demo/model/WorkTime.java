package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "work_time")

public class WorkTime {
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
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    //getter and setter
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(LocalTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    public LocalTime getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(LocalTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Float getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Float breakTime) {
        this.breakTime = breakTime;
    }

    public Float getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Float workTime) {
        this.workTime = workTime;
    }

    public Float getOverTime() {
        return overTime;
    }

    public void setOverTime(Float overTime) {
        this.overTime = overTime;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}



