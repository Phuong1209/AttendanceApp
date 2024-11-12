package com.example.demo.model.dto;

import java.util.List;

public class DepartmentSummaryDTO {
    private String name;
    private List<JobTypeSummaryDTO> jobTypeSummaries;

    //getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JobTypeSummaryDTO> getJobTypeSummaries() {
        return jobTypeSummaries;
    }

    public void setJobTypeSummaries(List<JobTypeSummaryDTO> jobTypeSummaries) {
        this.jobTypeSummaries = jobTypeSummaries;
    }
}


