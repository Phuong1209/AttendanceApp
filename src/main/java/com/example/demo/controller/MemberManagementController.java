package com.example.demo.controller;
//import com.example.demo.model.MemberManagement;
//import com.example.service.IMemberManagementService;
import com.example.demo.model.User;
import com.example.demo.service.IMemberManagementService;
import com.example.demo.service.MemberManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/member")
public class MemberManagementController {
    @Autowired
    private MemberManagementService memberManagementService;
    @GetMapping
    public Iterable<User> getAllUsers() {
        return memberManagementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        return userOptional.map(user -> new ResponseEntity<User>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
        user.setId();
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
