package com.example.demo.service;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentSummaryDTO;
import com.example.demo.model.Department;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IDepartmentService extends IGeneralService {
    List<DepartmentDTO> getAllDepartment();

    @Transactional
    Department save(Department model);

    List<User> getUserByDepartment(Long departmentId);
    List<DepartmentSummaryDTO> getSummaryByDepartment();
}
