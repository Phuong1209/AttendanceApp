package com.example.demo.dto.Summary;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class DepartmentSummaryDTO3 {
    String name;
    List<ProjectSummaryDTO3> projectSummaries;
}
