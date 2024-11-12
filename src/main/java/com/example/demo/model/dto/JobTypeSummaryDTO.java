package com.example.demo.model.dto;

import lombok.Data;

@Data
public class JobTypeSummaryDTO {
    private String name;
    private float totalTime;

    //getter and setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }
}
