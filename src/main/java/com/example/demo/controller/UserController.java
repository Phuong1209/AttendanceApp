package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")

public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getAllUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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

    //create
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> jobTypeOptional = userService.findById(id);
        if (!jobTypeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
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

}
