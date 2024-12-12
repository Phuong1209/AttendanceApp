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
    private Float breakTime = 0f; // Default to 0
    private Float workTime = 0f; // Default to 0
    private Float overTime = 0f; // Default to 0
    private UserDTO user;
    private Set<TaskDTO> tasks;

    private Boolean isHoliday = false; // Default to false
    private Boolean isWeekend = false; // Default to false
    private Boolean isFuture = false; // Default to false
    private String weekday = ""; // Default to an empty string

    public WorkTimeDTO() {}

    public WorkTimeDTO(Long id, LocalDate date, LocalDateTime checkinTime, LocalDateTime checkoutTime,
                       Float breakTime, Float workTime, Float overTime, UserDTO user, Set<TaskDTO> tasks,
                       Boolean isHoliday, Boolean isWeekend, Boolean isFuture, String weekday) {
        this.id = id;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.breakTime = breakTime != null ? breakTime : 0f;
        this.workTime = workTime != null ? workTime : 0f;
        this.overTime = overTime != null ? overTime : 0f;
        this.user = user;
        this.tasks = tasks;
        this.isHoliday = isHoliday != null ? isHoliday : false;
        this.isWeekend = isWeekend != null ? isWeekend : false;
        this.isFuture = isFuture != null ? isFuture : false;
        this.weekday = weekday != null ? weekday : "";
    }

    public void setWeekend(boolean b) {
    }

    public void setHoliday(boolean b) {
    }

    public void setFuture(boolean after) {
    }
}
