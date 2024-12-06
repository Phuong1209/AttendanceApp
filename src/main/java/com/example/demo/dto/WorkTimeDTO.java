package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class WorkTimeDTO {
    private Long id;
    private LocalDate date;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private Float breakTime;
    private Float workTime;
    private Float overTime;
    private UserDTO user;
    private Set<TaskDTO> tasks;

    // Added fields for the attendance sheet
    private boolean isHoliday;
    private boolean isWeekend;
    private boolean isFuture;
    private String weekday; // Add this to display the weekday name (月, 火, etc.)

    // Getter and setter for weekday field
    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    // Getters and Setters for boolean flags
    public boolean isHoliday() {
        return isHoliday;
    }
    public void setHoliday(boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    public boolean isWeekend() {
        return isWeekend;
    }
    public void setWeekend(boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    public boolean isFuture() {
        return isFuture;
    }
    public void setFuture(boolean isFuture) {
        this.isFuture = isFuture;
    }
}
