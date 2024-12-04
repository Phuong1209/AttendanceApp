package com.example.demo.dto.Summary;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class DepartmentSummaryDTO {
    private String name;
    private List<JobTypeSummaryDTO> jobTypeSummaries;
}
