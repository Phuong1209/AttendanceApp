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
}
