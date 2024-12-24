//Tuan Anh
package com.example.demo.dto.Summary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
public class DepJobSummaryDTO {
    private String departmentName;
    private String jobTypeName;
    private float totalTime;
}
