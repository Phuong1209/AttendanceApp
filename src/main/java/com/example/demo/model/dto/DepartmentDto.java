package com.example.demo.model.dto;

import com.example.demo.model.JobType;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public class DepartmentDto {
    @NotEmpty(message = "The name is required")
    private String name;
    private Set<JobType> jobTypes;
    //Multiple JobType Selection
    private List<Long> jobTypeIds;

    //getter & setter
    public @NotEmpty(message = "The name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name is required") String name) {
        this.name = name;
    }

    public Set<JobType> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(Set<JobType> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public List<Long> getJobTypeIds() {
        return jobTypeIds;
    }

    public void setJobTypeIds(List<Long> jobTypeIds) {
        this.jobTypeIds = jobTypeIds;
    }
}