package com.example.demo.service.Department;

import com.example.demo.model.Department;
import com.example.demo.model.dto.DepartmentDTO;
import com.example.demo.service.IGeneralService;

import java.util.List;

public interface IDepartmentService extends IGeneralService<Department> {
    List<DepartmentDTO> getAllDepartmentsWithUsers();
    //List<DepartmentSummaryDTO> getDepartmentSummaries();
}
