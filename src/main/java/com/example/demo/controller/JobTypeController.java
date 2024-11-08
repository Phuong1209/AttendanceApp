package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.service.Department.IDepartmentService;
import com.example.demo.service.JobType.IJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<JobType> createJobType(@RequestBody JobType jobType) {
        jobTypeService.save(jobType);
        return new ResponseEntity<>(jobType, HttpStatus.CREATED);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<JobType> editJobType(@PathVariable Long id, @RequestBody JobType jobType) {
        Optional<JobType> jobTypeOptional = jobTypeService.findById(id);
        if (!jobTypeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobType.setId(id);
        jobTypeService.save(jobType);
        return new ResponseEntity<>(jobType, HttpStatus.OK);
    }

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