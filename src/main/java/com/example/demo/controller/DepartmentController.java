package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentEditRequest;
import com.example.demo.dto.DepartmentSummaryDTO;
import com.example.demo.dto.JobTypeDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.Department.DepartmentService;
import com.example.demo.service.Department.IDepartmentService;
import jakarta.servlet.http.HttpServletResponse;
import com.example.demo.repository.IJobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/department")

public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IJobTypeRepository jobTypeRepository;
    @Autowired
    private IUserRepository userRepository;

    //show list department
    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        return ResponseEntity.ok().body(departmentService.findAll());
    }

    //show department by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> getAllDepartmentById(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        return departmentOptional.map(department -> new ResponseEntity<>(department, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //get list user of department
    @GetMapping("getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        List<User> users = departmentService.getUserByDepartment(id);
        return ResponseEntity.ok().body(users);
    }

    //get list jobtype of department
    @GetMapping("getJobType/{id}")
    public ResponseEntity<?> getJobType(@PathVariable Long id) {
        List<JobType> jobTypes = departmentService.getJobTypeByDepartment(id);
        return ResponseEntity.ok().body(jobTypes);
    }

    //create
    @PostMapping("")
    public ResponseEntity<Department> createUser(@RequestBody DepartmentDTO departmentDTO) {
        Department newDepartment = new Department();
        newDepartment.setName(departmentDTO.getName());

        //create jobtype list
        Set<JobTypeDTO> jobTypeDTOS = departmentDTO.getJobTypes();
        Set<JobType> jobTypes = new HashSet<>();
        for (JobTypeDTO jobTypeDTO : jobTypeDTOS) {
            JobType jobType;
            Optional<JobType> optionalJobType = jobTypeRepository.findById(jobTypeDTO.getId());
            if (optionalJobType.isPresent()) {
                jobType = optionalJobType.get();
            } else {
                jobType = new JobType();
                jobType.setName(jobTypeDTO.getName());
            }
            jobTypes.add(jobType);
        }

        //set list to created department
        newDepartment.setJobTypes(jobTypes);

        //save new department
        departmentService.save(newDepartment);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> editDepartment(
            @PathVariable("id") Long departmentId,
            @RequestBody DepartmentEditRequest editRequest) {
        DepartmentDTO updatedDepartment = departmentService.editDepartment(departmentId, editRequest.getName(), editRequest.getJobTypeIds());
        return ResponseEntity.ok(updatedDepartment);
    }


    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //summarize
    @GetMapping("/summarize")
    public ResponseEntity<List<DepartmentSummaryDTO>> getSummaryByDepartment() {
        List<DepartmentSummaryDTO> summaries = departmentService.getSummaryByDepartment();
        return ResponseEntity.ok(summaries);
    }
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
    //create (old)
/*    @PostMapping("")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        departmentService.save(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }*/

/*    //edit (old)
    @PutMapping("/{id}")
    public ResponseEntity<Department> editDepartment(@PathVariable Long id, @RequestBody Department department) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        department.setId(id);
        departmentService.save(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }*/

}