package com.example.demo.controller;

import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.IntrospectResponse;
import com.example.demo.service.AuthenticationService;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
//        try {
//            UserResponse userResponse = authService.registerUser(userRequest);
//            return ResponseEntity.ok(userResponse);
//        } catch (AppException e) {
//            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
//                    .body(e.getErrorCode().getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(ErrorCode.UNKNOWN_ERROR.getHttpStatus())
//                    .body(ErrorCode.UNKNOWN_ERROR.getMessage());
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserRequestLogin userRequest) {
//        try {
//            Optional<AuthenticationResponse> authResponse = authService.loginUser(userRequest);
//            if (authResponse.isPresent()) {
//                return ResponseEntity.ok(authResponse.get());
//            } else {
//                return ResponseEntity.status(ErrorCode.AUTHENTICATION_FAILED.getHttpStatus())
//                        .body(ErrorCode.AUTHENTICATION_FAILED.getMessage());
//            }
//        } catch (AppException e) {
//            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
//                    .body(e.getErrorCode().getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(ErrorCode.UNKNOWN_ERROR.getHttpStatus())
//                    .body(ErrorCode.UNKNOWN_ERROR.getMessage());
//        }
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
//        logger.info("Refreshing token");
//        try {
//            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//                throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
//            }
//            String refreshToken = authorizationHeader.substring(7);
//            AuthLoginResponse authResponse = authService.refreshToken(refreshToken);
//            return ResponseEntity.ok(authResponse);
//        } catch (AppException e) {
//            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
//                    .body(e.getErrorCode().getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(ErrorCode.UNKNOWN_ERROR.getHttpStatus())
//                    .body(ErrorCode.UNKNOWN_ERROR.getMessage());
//        }
//    }
}
