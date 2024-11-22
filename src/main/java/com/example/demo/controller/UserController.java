package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserEditRequest;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IPositionRepository;
import com.example.demo.service.User.IUserService;
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
        return ResponseEntity.ok().body(userService.findAll());
    }

    //show by Id
    @GetMapping("/{id}")
    public ResponseEntity<User> getAllUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //get list department, worktime, position
    @GetMapping("getDepartment/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id) {
        List<Department> departments = userService.getDepartmentByUser(id);
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("getWorkTime/{id}")
    public ResponseEntity<?> getWorkTime(@PathVariable Long id) {
        List<WorkTime> workTimes = userService.getWorkTimeByUser(id);
        return ResponseEntity.ok().body(workTimes);
    }

    @GetMapping("getPosition/{id}")
    public ResponseEntity<?> getPosition(@PathVariable Long id) {
        List<Position> positions = userService.getPositionByUser(id);
        return ResponseEntity.ok().body(positions);
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

        //Set Department list
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
    public ResponseEntity<UserDTO> editUser(
            @PathVariable("id") Long userId,
            @RequestBody UserEditRequest editRequest) {
        UserDTO updatedUser = userService.editUser(userId, editRequest.getUserName(),
                editRequest.getFullName(), editRequest.getPassword(),
                editRequest.getDepartmentIds(),editRequest.getPositionIds());
        return ResponseEntity.ok(updatedUser);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> jobTypeOptional = userService.findById(id);
        if (!jobTypeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //create (TA)
/*    @PostMapping("")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService1.createRequest(request));
        return response;
    }*/

    //show list (TA)
/*    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService1.getAllUsers();
    }*/

    //show by id (TA)
/*    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService1.getUserById(userId))
                .build();
    }*/

    //update (TA)
/*    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService1.updateUser(userId, request))
                .build();
    }*/

    //delete (TA)
/*    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable Long userId){
        userService1.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("deleted")
                .build();
    }*/



}
