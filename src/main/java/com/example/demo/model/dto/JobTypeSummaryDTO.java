package com.example.demo.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class JobTypeSummaryDTO {
    private String name;
    private float totalTime;

}
