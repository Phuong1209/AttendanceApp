package com.example.demo.service.Department;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.Summary.DepartmentSummaryDTO3;
import com.example.demo.dto.Summary.DepartmentSummaryDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.service.IGeneralService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Repository
public interface IDepartmentService {
    //CRUD
    List<DepartmentDTO> getAllDepartment();
    Department saveDepartment(Department department);
    DepartmentDTO findById(long departmentId);
    void updateDepartment(DepartmentDTO departmentDto);
    void delete(long departmentId);


    //show list jobType
    Set<JobType> findJobTypesByDepartment(Long departmentId);

    //show list user
    Set<User> findUsersByDepartment(Long departmentId);

    //summary by department
    List<DepartmentSummaryDTO> getSummaryByDepartment();
    //summary department-project
    List<DepartmentSummaryDTO3> getSummaryByDepartment3();

    //CSV
    void exportDepartmentSummaryToCSV(HttpServletResponse response, List<DepartmentSummaryDTO> summaries) throws IOException;

}
