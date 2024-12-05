package com.example.demo.controller;

import com.example.demo.csv.UserCSVExporter;
import com.example.demo.dto.*;
//import com.example.demo.dto.UserExcelDTO;
//import com.example.demo.excel.UserExcelExporter;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;

import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IPositionRepository;
import com.example.demo.service.Department.DepartmentService;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IPositionRepository;
import com.example.demo.service.Department.IDepartmentService;
import com.example.demo.service.User.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.User.UserService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService1;
    //P
    @Autowired
    private IUserService userService;
    @Autowired
    IPositionRepository positionRepository;
    @Autowired
    IDepartmentRepository departmentRepository;
    @Autowired
    DepartmentService departmentService;
    PasswordEncoder passwordEncoder;

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService1.getMyInfo())
                .build();
    }

    //show list
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUser());

    }

    //show by Id
    @GetMapping("/{id}")
    public ResponseEntity<User> getAllUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //get list department, worktime, position
    @GetMapping("getPosition/{id}")
    public ResponseEntity<?> getPosition(@PathVariable Long id) {
        List<Position> positions = userService.getPositionByUser(id);
        return ResponseEntity.ok().body(positions);
    }

    @GetMapping("getDepartment/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id) {
        List<Department> departments = userService.getDepartmentByUser(id);
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("getWorkingTime/{id}")
    public ResponseEntity<?> getWorkingTime(@PathVariable Long id) {
        List<WorkTime> workingTimes = userService.getWorkTimeByUser(id);
        return ResponseEntity.ok().body(workingTimes);
    }

    //create
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User newUser = new User();
        newUser.setUserName(userDTO.getUserName());
        newUser.setFullName(userDTO.getFullName());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //Set Position list
        Set<PositionDTO> positionDTOS = userDTO.getPositions();
        Set<Position> positions = new HashSet<>();
        for (PositionDTO positionDTO : positionDTOS) {
            Position position;
            Optional<Position> optionalPosition = positionRepository.findById(positionDTO.getId());
            if (optionalPosition.isPresent()) {
                position = optionalPosition.get();
            } else {
                position = new Position();
                position.setName(positionDTO.getName());
            }
            positions.add(position);
        }
        newUser.setPositions(positions);

        //Set department of departmentDTOs for department
        Set<DepartmentDTO> departmentDTOS = userDTO.getDepartments();
        Set<Department> departments = new HashSet<>();
        for (DepartmentDTO departmentDTO : departmentDTOS) {
            Department department;
            Optional<Department> optionalDepartment = departmentRepository.findById(departmentDTO.getId());
            if (optionalDepartment.isPresent()) {
                department = optionalDepartment.get();
            } else {
                department = new Department();
                department.setName(departmentDTO.getName());
            }
            departments.add(department);
        }
        newUser.setDepartments(departments);

        userService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User existingUser = userOptional.get();
        if(userDTO.getId() != null){
            existingUser.setId(userDTO.getId());
        }
        if(userDTO.getUserName() != null){
            existingUser.setUserName(userDTO.getUserName());
        }
        if(userDTO.getFullName() != null){
            existingUser.setFullName(userDTO.getFullName());
        }
        if (userDTO.getPassword() != null){
            existingUser.setPassword(userDTO.getPassword());
        }
        //Get old position list
        Set<Position> oldPositions = existingUser.getPositions();

        // 新しいポジションIDリストをDTOから取得
        Set<Long> newPositionIds = userDTO.getPositions().stream()
                .map(PositionDTO::getId)
                .collect(Collectors.toSet());
        Set<Position> newPositions = new HashSet<>();
        for (Long positionId : newPositionIds) {
            Position position;
            Optional<Position> optionalPosition = positionRepository.findById(positionId);
            if (optionalPosition.isPresent()) {
                newPositions.add(optionalPosition.get());
            } else {
                // ポジションが存在しない場合の処理（必要ならエラーを返す）
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            //get list of positions to delete
            Set<Position> positionsToRemove = new HashSet<>(oldPositions);
            positionsToRemove.removeAll(newPositions);

            //get list of positions to add
            Set<Position> positionsToAdd = new HashSet<>(newPositions);
            positionsToAdd.removeAll(oldPositions);

            // Update the user's positions
            existingUser.getPositions().removeAll(positionsToRemove);
            existingUser.getPositions().addAll(positionsToAdd);

            userService.save(existingUser);

            //Set department of departmentDTOs for department

            //get list of old departments
            Set<Department> oldDepartments = existingUser.getDepartments();
            //get new list of departmentIds from DTO
            Set<Long> newDepartmentIds = userDTO.getDepartments().stream()
                    .map(DepartmentDTO::getId)
                    .collect(Collectors.toSet());
            Set<Department> newDepartments = new HashSet<>();
            for (Long departmentId : newDepartmentIds) {
                Department department;
                Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
                if (optionalDepartment.isPresent()) {
                    newDepartments.add(optionalDepartment.get());
                } else {
                        // ポジションが存在しない場合の処理（必要ならエラーを返す）
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }

//               // get list of positions to delete
                Set<Department> departmentsToRemove = new HashSet<>(oldDepartments);
               departmentsToRemove.removeAll(newDepartments);

                //get list of positions to add
                Set<Department> departmentsToAdd = new HashSet<>(newDepartments);
                departmentsToAdd.removeAll(oldDepartments);

                // Update the user's positions
                existingUser.getDepartments().removeAll(departmentsToRemove);
                existingUser.getDepartments().addAll(departmentsToAdd);
            }

            userService.save(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.NO_CONTENT);
    }
}
