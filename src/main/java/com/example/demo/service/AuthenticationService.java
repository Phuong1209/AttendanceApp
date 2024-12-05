package com.example.demo.service;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.response.UserResponse;
//import com.example.demo.enums.Notification;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Position;
import com.example.demo.repository.IPositionRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.IntrospectResponse;
import com.example.demo.model.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.IUserRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    IUserRepository userRepository;
    IPositionRepository positionRepository;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;
    private UserMapper userMapper;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime  = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUserName(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED); // Return an error if passwords do not match
        }

        String tokenResponse = generateToken(user);
        return AuthenticationResponse.builder()
                .token(tokenResponse)
                .authenticated(true)
                .build();

    }
    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e) {
            log.error("cannot sign JWT object", e);
            throw new RuntimeException(e);
        }
    }
    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
//        if (!CollectionUtils.isEmpty(user.getRoles())){
//            user.getRoles().forEach(stringJoiner::add);
//        }
        user.getPositions().forEach(position -> stringJoiner.add(position.getName()));
        return stringJoiner.toString();
    }

//    @Transactional
//    public UserResponse registerUser(UserCreationRequest userCreationRequest) {
//        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
//            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
//        }
//
//        User user = userMapper.toUser(userCreationRequest);
//        user.setUser_passwords(passwordEncoder.encode(user.getUser_passwords()));
//        Position userPosition = positionRepository.findByPositionname(com.example.demo.enums.Position.USER.des())
//                .orElseGet(() -> {
//                    Position newPosition = Position.builder()
//                            .positionname(com.example.demo.enums.Position.USER.des())
////                            .description(Notification.REGISTER.des())
//                            .build();
//                    return positionRepository.save(newPosition);
//                });
//        user.setPositions(new HashSet<>(Collections.singletonList(userPosition)));
//        User saveUser = userRepository.save(user);
//        return userMapper.toUserResponse(saveUser);
//    }


//    @Transactional
//    public AuthLoginResponse refreshToken(String refreshToken) {
//        if (!jwtTokenProvider.validateToken(refreshToken) || jwtTokenProvider.isTokenExpired(refreshToken)) {
//            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
//        }
//        String username = jwtTokenProvider.getUsernameFromJWT(refreshToken);
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isEmpty()) {
//            throw new AppException(ErrorCode.USER_NOT_FOUND);
//        }
//        AuthLoginResponse authResponse = new AuthLoginResponse();
//        Set<Position> positions = user.get().getPositions();
//        String newAccessToken = jwtTokenProvider.generateToken(user, positions);
//        authResponse.setAccessToken(newAccessToken);
//        authResponse.setRefreshToken(refreshToken);
//        Optional<Session> currentSession = sessionService.getSession(email);
//        if (currentSession.isPresent()) {
//            Session session = currentSession.get();
//            authResponse.setSessionResponse(new SessionResponse(session.getId(), session.getCreatedAt(), session.getExpiresAt()));
//        } else {
//            Session newSession = sessionService.createSession(user.get());
//            authResponse.setSessionResponse(new SessionResponse(newSession.getId(), newSession.getCreatedAt(), newSession.getExpiresAt()));
//        }
//        if (authResponse.getSessionResponse() == null) {
//            throw new AppException(ErrorCode.INVALID_RESPONSE_CONTENT);
//        }
//        return authResponse;
//    }
}
