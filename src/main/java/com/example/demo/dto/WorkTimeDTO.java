package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Data
@Builder

public class WorkTimeDTO {
    private Long id;
    private LocalDate date;
    private LocalDateTime checkinTime;
    private LocalDateTime checkoutTime;
    private Float breakTime;
    private Float workTime;
    private Float overTime;
    private UserDTO user;
    private Set<TaskDTO> tasks;

    //constructor
    public WorkTimeDTO() {
    }

    public WorkTimeDTO(Long id, LocalDate date,
                       LocalDateTime checkinTime, LocalDateTime checkoutTime,
                       Float breakTime, Float workTime, Float overTime) {
        this.id = id;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.breakTime = breakTime;
        this.workTime = workTime;
        this.overTime = overTime;
    }

    public WorkTimeDTO(Long id, LocalDate date, LocalDateTime checkinTime, LocalDateTime checkoutTime, Float breakTime, Float workTime, Float overTime, UserDTO user, Set<TaskDTO> tasks) {
        this.id = id;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.breakTime = breakTime;
        this.workTime = workTime;
        this.overTime = overTime;
        this.user = user;
        this.tasks = tasks;
    }
}
