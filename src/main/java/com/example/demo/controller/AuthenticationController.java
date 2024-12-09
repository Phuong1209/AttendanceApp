package com.example.demo.controller;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.BlacklistService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    AuthenticationService authenticationService;
    private final BlacklistService blacklistService;

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
//    public ResponseEntity<?> register(@RequestBody UserCreationRequest userCreationRequest) {
//        try {
//            UserResponse userResponse = authenticationService.registerUser(userCreationRequest);
//            return ResponseEntity.ok(userResponse);
//        } catch (AppException e) {
//            return ResponseEntity.status(e.getErrorCode().getHttpStatusCode())
//                    .body(e.getErrorCode().getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(ErrorCode.UNKNOWN_ERROR.getHttpStatusCode())
//                    .body(ErrorCode.UNKNOWN_ERROR.getMessage());
//        }
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            blacklistService.blacklistToken(token);
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
        }
    }


//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
//        logger.info("Refreshing token");
//        try {
//            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//                throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
//            }
//            String refreshToken = authorizationHeader.substring(7);
//            AuthLoginResponse authResponse = authenticationService.refreshToken(refreshToken);
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
