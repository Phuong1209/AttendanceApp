package com.example.demo.controller;


import com.example.demo.dto.JobTypeDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.service.Department.IDepartmentService;
import com.example.demo.service.JobType.IJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/jobtype")

public class JobTypeController {
    @Autowired
    private IJobTypeService jobTypeService;

    //show list
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<JobType>> getAllJobType() {
        List<JobType> jobTypeList = (List<JobType>) jobTypeService.findAll();
        if (jobTypeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobTypeList, HttpStatus.OK);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<JobType> findJobTypesById(@PathVariable Long id) {
        Optional<JobType> jobTypeOptional = jobTypeService.findById(id);
        return jobTypeOptional.map(JobType -> new ResponseEntity<>(JobType, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //create
    @PostMapping("")
    public ResponseEntity<JobType> createJobType(@RequestBody JobTypeDTO jobTypeDTO) {
        JobType newJobType = new JobType();
        newJobType.setName(jobTypeDTO.getName());

        //save new jobType
        jobTypeService.save(newJobType);
        return new ResponseEntity<>(newJobType, HttpStatus.CREATED);
    }

    //edit
/*    @PutMapping("/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            // Fetch the existing WorkTime record
            Optional<JobType> optionalJobType = jobTypeService.findById(id);
            if (optionalJobType.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JobType not found.");
            }
            JobType existingJobType = optionalJobType.get();

            // Parse request body for editable fields
            if (requestBody.containsKey("name")) {
                existingJobType.setName(String.valueOf((String) requestBody.get("name")));
            }

            // Save updated JobType
            JobType updatedJobType = jobTypeService.save(existingJobType);

            // Map JobType to DTO
            JobTypeDTO jobTypeDTO = new JobTypeDTO();
            jobTypeDTO.setId(updatedJobType.getId());
            jobTypeDTO.setName(updatedJobType.getName());

            // Return response
            return ResponseEntity.ok(jobTypeDTO);

        } catch (Exception e) {
            // Handle exceptions gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }*/

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobType(@PathVariable Long id) {
        Optional<JobType> jobTypeOptional = jobTypeService.findById(id);
        if (!jobTypeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobTypeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}