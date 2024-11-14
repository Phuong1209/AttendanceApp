package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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
    //private Set<TaskDto> tasks;
    private UserDTO user;
}
