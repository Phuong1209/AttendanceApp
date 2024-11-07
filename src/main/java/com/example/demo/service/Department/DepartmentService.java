package com.example.demo.service.Department;

import com.example.demo.model.Department;
import com.example.demo.repository.IDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }
    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department save(Department model) {
        return departmentRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        departmentRepository.deleteById(id);
    }

}

