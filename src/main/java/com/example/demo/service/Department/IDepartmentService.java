package com.example.demo.service.Department;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentSummaryDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IDepartmentService extends IGeneralService<Department> {
    //get list department
    List<DepartmentDTO> getAllDepartment();
    //get list user of department
    List<User> getUserByDepartment(Long departmentId);
    //get list jobType of department
    List<JobType> getJobTypeByDepartment(Long departmentId);
    //summary by department
    List<DepartmentSummaryDTO> getSummaryByDepartment();
    //edit department
    DepartmentDTO editDepartment(Long departmentId, String name, Set<Long> jobTypeIds);
}
