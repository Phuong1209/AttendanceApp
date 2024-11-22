package com.example.demo.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
//import security.exception.ErrorCode;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserCreationRequest {
    @Size (min = 3, message = "USERNAME_INVALID")
    private String userName;
    private String userFullName;
    @Size(min = 8, message = "PASSWORD_TOO_SHORT")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$", message = "PASSWORD_INVALID")
    private String userPasswords;
}
