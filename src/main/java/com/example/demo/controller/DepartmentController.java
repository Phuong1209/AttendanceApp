package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.model.dto.DepartmentDTO;
import com.example.demo.model.dto.DepartmentSummaryDTO;
import com.example.demo.model.dto.JobTypeDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.Department.IDepartmentService;
import com.example.demo.service.JobType.IJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

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

    //edit (new)
/*    @PutMapping("/{id}")
    public ResponseEntity<Department> editDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        Optional<Department> departmentOptional = departmentService.findById(id);
        if (departmentOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Department existingDepartment = departmentOptional.get();
        existingDepartment.setId(departmentDTO.getId());
        existingDepartment.setName(departmentDTO.getName());

        //Edit list JobType
        Set<JobTypeDTO> jobTypeDTOS = departmentDTO.getJobTypes();
        Set<JobType> jobTypes = new HashSet<>();
        for (JobTypeDTO jobTypeDTO : jobTypeDTOS) {
            JobType jobType;
            Optional<JobType> optionalJobType = jobTypeRepository.findById(jobTypeDTO.getId());
            if (optionalJobType.isPresent()) {
                jobType = optionalJobType.get();
            }
            jobType = new JobType();
            jobType.setId(jobTypeDTO.getId());
            jobType.setName(jobTypeDTO.getName());
            jobTypes.add(jobType);
        }
        existingDepartment.setJobTypes(jobTypes);

        departmentService.save(existingDepartment);
        return new ResponseEntity<>(existingDepartment, HttpStatus.OK);
    }*/

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