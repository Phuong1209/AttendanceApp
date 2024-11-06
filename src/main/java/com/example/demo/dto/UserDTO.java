package com.example.demo.dto;

import lombok.Data;

import java.util.Set;
@Data
public class UserDTO {
    private String userName;
    private String userFullName;
    private Set<String> positions;
    private Set<String> departments;
}
