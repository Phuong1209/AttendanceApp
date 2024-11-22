package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.Department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    //create (old)
/*    @PostMapping("")
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
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
    }*/

    //create (new)
    @PostMapping("")
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            Department newDepartment = new Department();
            newDepartment.setName(departmentDTO.getName());

            // Create jobType list
            Set<JobTypeDTO> jobTypeDTOS = departmentDTO.getJobTypes();
            Set<JobType> jobTypes = new HashSet<>();

            // Validate jobType IDs
            for (JobTypeDTO jobTypeDTO : jobTypeDTOS) {
                Optional<JobType> optionalJobType = jobTypeRepository.findById(jobTypeDTO.getId());
                if (optionalJobType.isEmpty()) {
                    // If any jobType ID is invalid, return error
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Error: JobType with ID " + jobTypeDTO.getId() + " not found.");
                }
                jobTypes.add(optionalJobType.get());
            }

            // Set list to created department
            newDepartment.setJobTypes(jobTypes);

            // Save new department
            departmentService.save(newDepartment);
            return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);

        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    //edit (old)
   /* @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> editDepartment(
            @PathVariable("id") Long departmentId,
            @RequestBody DepartmentEditRequest editRequest) {
        DepartmentDTO updatedDepartment = departmentService.editDepartment(departmentId, editRequest.getName(), editRequest.getJobTypeIds());
        return ResponseEntity.ok(updatedDepartment);
    }*/

    //edit (new)
    @PutMapping("/{id}")
    public ResponseEntity<?> editDepartment(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            // Fetch the department by ID
            Optional<Department> optionalDepartment = departmentService.findById(id);
            if (optionalDepartment.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found with ID: " + id);
            }
            Department department = optionalDepartment.get();

            // Update the department's name
            if (requestBody.containsKey("name")) {
                String newName = (String) requestBody.get("name");
                if (newName != null && !newName.trim().isEmpty()) {
                    department.setName(newName);
                }
            }

            // Update the department's job types
            if (requestBody.containsKey("jobTypes")) {
                List<Map<String, Object>> jobTypesList = (List<Map<String, Object>>) requestBody.get("jobTypes");
                Set<Long> jobTypeIds = jobTypesList.stream()
                        .map(jobType -> ((Number) jobType.get("id")).longValue())
                        .collect(Collectors.toSet());

                List<JobType> newJobTypes = jobTypeRepository.findAllById(jobTypeIds);

                // Validate that all passed JobType IDs exist
                if (newJobTypes.size() != jobTypeIds.size()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("One or more JobType IDs are invalid.");
                }

                department.setJobTypes(new HashSet<>(newJobTypes));
            }

            // Save the updated department
            Department updatedDepartment = departmentService.save(department);

            // Map updated department to DepartmentDTO
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(updatedDepartment.getId());
            departmentDTO.setName(updatedDepartment.getName());

            Set<JobTypeDTO> jobTypeDTOS = updatedDepartment.getJobTypes().stream()
                    .map(jobType -> {
                        JobTypeDTO jobTypeDTO = new JobTypeDTO();
                        jobTypeDTO.setId(jobType.getId());
                        jobTypeDTO.setName(jobType.getName());
                        return jobTypeDTO;
                    })
                    .collect(Collectors.toSet());

            departmentDTO.setJobTypes(jobTypeDTOS);

            // Map users to UserDTO
            Set<UserDTO> userDTOS = updatedDepartment.getUsers().stream()
                    .map(user -> {
                        UserDTO userDTO = new UserDTO();
                        userDTO.setId(user.getId());
                        userDTO.setFullName(user.getFullName());
                        userDTO.setUserName(user.getUserName());
                        return userDTO;
                    })
                    .collect(Collectors.toSet());
            departmentDTO.setUsers(userDTOS);

            return ResponseEntity.ok(departmentDTO);

        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
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