//Thanh Phuong & Mai Huong
package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Data
@Builder

public class WorkTimeDTO {
    private Long id;
    private LocalDate date;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private Double breakTime;
    private Double workTime;
    private Double overTime;
    private UserDTO user;
    private Set<TaskDTO> tasks;

    // Added fields for the attendance sheet (MaiHuong)
    private boolean isHoliday;
    private boolean isWeekend;
    private boolean isFuture;
    private String weekday; // Add this to display the weekday name (月, 火, etc.)

    //constructor
    public WorkTimeDTO() {
    }

    public WorkTimeDTO(Long id, LocalDate date,
                       LocalTime checkinTime, LocalTime checkoutTime,
                       Double breakTime, Double workTime, Double overTime) {
        this.id = id;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.breakTime = breakTime;
        this.workTime = workTime;
        this.overTime = overTime;
    }

    public WorkTimeDTO(Long id, LocalDate date, LocalTime checkinTime, LocalTime checkoutTime, Double breakTime, Double workTime, Double overTime, UserDTO user, Set<TaskDTO> tasks, boolean isHoliday, boolean isWeekend, boolean isFuture, String weekday) {
        this.id = id;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.breakTime = breakTime;
        this.workTime = workTime;
        this.overTime = overTime;
        this.user = user;
        this.tasks = tasks;
        this.isHoliday = isHoliday;
        this.isWeekend = isWeekend;
        this.isFuture = isFuture;
        this.weekday = weekday;
    }
}
