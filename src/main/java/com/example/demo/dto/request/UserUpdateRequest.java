package com.example.demo.dto.request;

import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.WorkingTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    Long id;
    String username;
    String user_passwords;
    String user_fullname;
    Set<Position> positions;
    Set<Department> departments;
    Set<WorkingTime> workingTimes;
}
