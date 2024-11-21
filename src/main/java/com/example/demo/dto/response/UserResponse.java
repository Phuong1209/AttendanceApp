package com.example.demo.dto.response;

import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.WorkTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String user_passwords;
    String user_fullname;
    Set<Position> positions;
    Set<Department> departments;
    Set<WorkTime> workTimes;

}
