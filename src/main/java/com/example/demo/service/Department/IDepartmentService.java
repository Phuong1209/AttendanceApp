package com.example.demo.service.Department;

import com.example.demo.model.Department;
import com.example.demo.model.dto.DepartmentSummaryDTO;
import com.example.demo.service.IGeneralService;

import java.util.List;

public interface IDepartmentService extends IGeneralService<Department> {

    List<DepartmentSummaryDTO> getDepartmentSummaries();
}
