package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    // Get all departments
    @GetMapping("")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.findAll();
        if (departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    // Get a department by ID
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.findById(id);
        return department.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new department
    @PostMapping("")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        departmentService.save(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    // Update an existing department
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Optional<Department> existingDepartment = departmentService.findById(id);
        if (!existingDepartment.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        department.setId(id); // Set the ID to update the correct entity
        departmentService.save(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    // Delete a department by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable Long id) {
        Optional<Department> department = departmentService.findById(id);
        if (!department.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Optional: Remove job types from a department
    @PutMapping("/{id}/jobtypes/remove")
    public ResponseEntity<Department> removeJobTypesFromDepartment(@PathVariable Long id, @RequestBody Set<JobType> jobTypes) {
        Optional<Department> department = departmentService.findById(id);
        if (!department.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Department existingDepartment = department.get();
        existingDepartment.getJobtypes().removeAll(jobTypes); // Remove the specified job types
        departmentService.save(existingDepartment);
        return new ResponseEntity<>(existingDepartment, HttpStatus.OK);
    }
}
