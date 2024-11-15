package com.example.demo.service.Department;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.model.dto.DepartmentDTO;
import com.example.demo.model.dto.DepartmentSummaryDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartmentService extends IGeneralService<Department> {
    List<DepartmentDTO> getAllDepartment();
    List<User> getUserByDepartment(Long departmentId);
    List<DepartmentSummaryDTO> getSummaryByDepartment();
}
