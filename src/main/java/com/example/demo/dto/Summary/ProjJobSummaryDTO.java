package com.example.demo.dto.Summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
public class ProjJobSummaryDTO {
    private String projectCode;
    private String projectName;
    private String jobTypeName;
    private float totalTime;
}
