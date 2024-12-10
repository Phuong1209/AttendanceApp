package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.Summary.DepartmentSummaryDTO;
import com.example.demo.dto.Summary.DepartmentSummaryDTO3;
import com.example.demo.model.Department;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.service.Department.DepartmentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/department")

public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private IJobTypeRepository jobTypeRepository;

    //show list
    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        return ResponseEntity.ok().body(departmentService.getAllDepartment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.findById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department department = departmentService.mapToDepartment(departmentDTO);
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDto) {
        departmentDto.setId(id);
        departmentService.updateDepartment(departmentDto);
        Department department = departmentService.mapToDepartment(departmentDto);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //summarize JobType
    @GetMapping("/summarize")
    public ResponseEntity<List<DepartmentSummaryDTO>> getSummaryByDepartment() {
        List<DepartmentSummaryDTO> summaries = departmentService.getSummaryByDepartment();
        return ResponseEntity.ok(summaries);
    }

    //summarize project
    @GetMapping("/summarize3")
    public ResponseEntity<List<DepartmentSummaryDTO3>> getSummaryByDepartment3() {
        List<DepartmentSummaryDTO3> summaries = departmentService.getSummaryByDepartment3();
        return ResponseEntity.ok(summaries);
    }

    //CSV
    @GetMapping("/exportCSV")
    public void generateCSV(HttpServletResponse response) throws IOException {
        List<DepartmentSummaryDTO> summaries = departmentService.getSummaryByDepartment();
        response.setContentType("CSVpplication/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        departmentService.exportDepartmentSummaryToCSV(response,summaries);
    }
}