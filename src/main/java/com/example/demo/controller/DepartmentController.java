package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.service.Department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/department")

public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    //show list
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Department>> getAllDepartment() {
        List<Department> departmentList = (List<Department>) departmentService.findAll();
        if (departmentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> findDepartmentsById(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        return departmentOptional.map(Department -> new ResponseEntity<>(Department, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //create
    @PostMapping("")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        departmentService.save(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<Department> editDepartment(@PathVariable Long id, @RequestBody Department department) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        department.setId(id);
        departmentService.save(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

