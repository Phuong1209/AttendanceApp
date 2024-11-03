package com.example.demo.controller;
//import com.example.demo.model.MemberManagement;
//import com.example.service.IMemberManagementService;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkingTime;
import com.example.demo.service.IMemberManagementService;
import com.example.demo.service.MemberManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/member")
public class MemberManagementController {
    @Autowired
    private MemberManagementService memberManagementService;
    @GetMapping

    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok().body(memberManagementService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        return userOptional.map(user -> new ResponseEntity<User>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("getPosition/{id}")
    public ResponseEntity<?> getPosition(@PathVariable Long id) {
        List<Position> positions = memberManagementService.getPositionByUser(id);
        return ResponseEntity.ok().body(positions);
    }
    @GetMapping("getDepartment/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id){
        List<Department>departments=memberManagementService.getDepartmentByUser(id);
        return ResponseEntity.ok().body(departments);
    }
    @GetMapping("getWorkingTime/{id}")
    public ResponseEntity<?>getWorkingTime(@PathVariable Long id){
        List<WorkingTime>workingTimes=memberManagementService.getWorkingTimebyUser(id);
        return ResponseEntity.ok().body(workingTimes);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        memberManagementService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        memberManagementService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        memberManagementService.remove(id);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.NO_CONTENT);
    }

}