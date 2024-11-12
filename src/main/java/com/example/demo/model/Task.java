package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "task")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float totalTime;
    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "work_time_id")
    private WorkTime workTime;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "job_type_id")
    private JobType jobType;

    //getter and setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
}