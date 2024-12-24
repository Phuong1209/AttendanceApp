//Tuan Anh
package com.example.demo.dto.Summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
public class DepProjSummaryDTO {
    private String departmentName;
    private String projectCode;
    private String projectName;
    private float totalTime;

    public DepProjSummaryDTO(String departmentName, String projectCode, String projectName, float totalTime) {
        this.departmentName = departmentName;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.totalTime = totalTime;
    }
}
