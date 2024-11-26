package com.example.demo.service.Department;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentSummaryDTO3;
import com.example.demo.dto.DepartmentSummaryDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.service.IGeneralService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Repository;

import java.io.IOException;
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
    //summary department+project
    List<DepartmentSummaryDTO3> getSummaryByDepartment3();
    //edit department
    DepartmentDTO editDepartment(Long departmentId, String name, Set<Long> jobTypeIds);

    //CSV
    void exportDepartmentSummaryToCSV(HttpServletResponse response, List<DepartmentSummaryDTO> summaries) throws IOException;
}
