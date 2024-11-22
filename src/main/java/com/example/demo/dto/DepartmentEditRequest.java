package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class DepartmentEditRequest {
    private String name;
    private Set<Long> jobTypeIds;
}