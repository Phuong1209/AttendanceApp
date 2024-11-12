package com.example.demo.controller;

import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.service.JobType.IJobTypeService;
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

    //show list
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getAllJobType() {
        List<User> jobTypeList = (List<User>) userService.findAll();
        if (jobTypeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobTypeList, HttpStatus.OK);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<User> findUsersById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(JobType -> new ResponseEntity<>(JobType, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
