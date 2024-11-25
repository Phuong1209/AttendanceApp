package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public enum ErrorCode {
    USER_NOT_FOUND(404, "User Not Found",HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(409, "User Already Exists", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(400, "Username is invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(400, "Password is invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT(400, "Password is too short", HttpStatus.BAD_REQUEST),
    INVALID_KEY(400, "Invalid Key", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAILED(400, "Authentication Failed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(400, "Access Denied", HttpStatus.FORBIDDEN),
    UNKNOWN_ERROR(500, "An unknown error occurred. Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}
