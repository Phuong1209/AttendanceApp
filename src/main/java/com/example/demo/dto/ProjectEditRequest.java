package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class ProjectEditRequest {
    private String name;
    private String code;
    private Set<Long> taskIds;
}