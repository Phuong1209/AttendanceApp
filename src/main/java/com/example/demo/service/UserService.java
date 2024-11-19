package com.example.demo.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.enums.Position;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.IPositionRepository;
import com.example.demo.repository.IUserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    IPositionRepository positionRepository;
    PasswordEncoder passwordEncoder;
    IUserRepository userRepository;
    UserMapper userMapper;
    public UserResponse createRequest(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User user = userMapper.toUser(request);
        user.setUser_passwords(passwordEncoder.encode(user.getUser_passwords()));
        HashSet<String> position = new HashSet<>();
        position.add(Position.USER.name());
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse getMyInfo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return userMapper.toUserResponse(user);
    }
    @PreAuthorize("hasPosition('ADMIN')")
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(Long id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

public UserResponse updateUser(Long userId, UserUpdateRequest request) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    userMapper.updateUser(user, request);
    user.setUser_passwords(passwordEncoder.encode(request.getUser_passwords()));
    user.setPositions(new HashSet<>(positionRepository.findAllById(Collections.singleton(userId))));
    return userMapper.toUserResponse(userRepository.save(user));
}
    @PreAuthorize("hasPosition('ADMIN')")
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
