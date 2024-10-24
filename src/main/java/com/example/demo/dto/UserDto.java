package com.example.demo.dto;

import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.WorkingTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder

public class UserDto {
    private Long id;
    private String user_name;
    private String user_fullname;
    private String user_passwords;
}
