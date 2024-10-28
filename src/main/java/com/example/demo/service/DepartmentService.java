package com.example.demo.service;

import com.example.demo.dto.DepartmentDto;
import java.util.List;


public interface DepartmentService {
    List<DepartmentDto> findAllDepartments();
}
