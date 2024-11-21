package com.example.demo.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse {
    private String userName;
    private String userFullName;
    private String userPasswords;
    private Set<PositionResponse> positions;
}
