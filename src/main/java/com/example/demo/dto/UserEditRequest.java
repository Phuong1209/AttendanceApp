package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class UserEditRequest {
    private String userName;
    private String fullName;
    private String password;
    private Set<Long> departmentIds;
    private Set<Long> positionIds;
}
