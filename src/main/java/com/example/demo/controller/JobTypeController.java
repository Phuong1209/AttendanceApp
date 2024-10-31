package com.example.demo.controller;

import com.example.demo.model.JobType;
import com.example.demo.service.IJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/jobtypes")
public class JobTypeController {

    @Autowired
    private IJobTypeService jobTypeService;

    // Fetch all JobTypes
    @GetMapping("{/id}")
    public ResponseEntity<List<JobType>> getAllJobTypes() {
        List<JobType> jobTypes = jobTypeService.findAll();
        return jobTypes.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(jobTypes, HttpStatus.OK);
    }

    // Fetch JobType by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobType> getJobTypeById(@PathVariable Long id) {
        Optional<JobType> jobType = jobTypeService.findById(id);
        return jobType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create new JobType
    @PostMapping
    public ResponseEntity<JobType> createJobType(@RequestBody JobType jobType) {
        jobTypeService.save(jobType);
        return new ResponseEntity<>(jobType, HttpStatus.CREATED);
    }

    // Update existing JobType
    @PutMapping("/{id}")
    public ResponseEntity<JobType> editJobType(@PathVariable Long id, @RequestBody JobType jobType) {
        if (!jobTypeService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobType.setId(id);
        jobTypeService.save(jobType);
        return new ResponseEntity<>(jobType, HttpStatus.OK);
    }

    // Delete JobType
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteJobType(@PathVariable Long id) {
        if (!jobTypeService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobTypeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
