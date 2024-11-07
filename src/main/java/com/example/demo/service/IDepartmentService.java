package com.example.demo.service;

import com.example.demo.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    List<Department> findAll();
    Optional<Department> findById(Long id);
    Department save(Department department);
    void remove(Long id);
}
