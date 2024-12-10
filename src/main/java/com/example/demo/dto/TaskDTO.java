package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

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
    public TaskDTO(Long id, float totalTime, String comment, WorkTimeDTO workTime, ProjectDTO project, JobTypeDTO jobType) {
        this.id = id;
        this.totalTime = totalTime;
        this.comment = comment;
        this.workTime = workTime;
        this.project = project;
        this.jobType = jobType;
    }
}
