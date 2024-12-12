package com.example.demo.dto.Summary;

import com.example.demo.model.JobType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter

public class DepartmentSummaryDTO {
    private String name;
    private List<JobTypeSummaryDTO> jobTypeSummaries;

}
