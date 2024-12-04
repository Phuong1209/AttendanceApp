package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TaskDTO {
    private Long id;
    private float totalTime;
    private String comment;
    private WorkTimeDTO workTime;
    private ProjectDTO project;
    private JobTypeDTO jobType;

    //constructor
    public TaskDTO(Long id, String comment, float totalTime) {
    }
    public TaskDTO() {
    }
}
