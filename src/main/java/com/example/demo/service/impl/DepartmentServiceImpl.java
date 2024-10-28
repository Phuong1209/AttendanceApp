package com.example.demo.service.impl;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDto> findAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToDepartmentDto).collect(Collectors.toList());
    }

    private DepartmentDto mapToDepartmentDto(Department department) {
        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
        return departmentDto;
    }
}
