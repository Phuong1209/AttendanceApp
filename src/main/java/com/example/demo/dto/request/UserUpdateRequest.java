package com.example.demo.dto.request;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserUpdateRequest {
    private String userName;
    private String userFullName;
    private String userPasswords;
}
