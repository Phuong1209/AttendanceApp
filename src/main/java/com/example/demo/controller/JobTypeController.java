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

    // Lấy danh sách tất cả JobTypes
    @GetMapping("")
    public ResponseEntity<List<JobType>> getAllJobTypes() {
        List<JobType> jobTypes = jobTypeService.findAll();
        if (jobTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobTypes, HttpStatus.OK);
    }

    // Lấy thông tin của một JobType dựa trên id
    @GetMapping("/{id}")
    public ResponseEntity<JobType> getJobTypeById(@PathVariable Long id) {
        Optional<JobType> jobType = jobTypeService.findById(id);
        return jobType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Tạo mới một JobType
    @PostMapping("")
    public ResponseEntity<JobType> createJobType(@RequestBody JobType jobType) {
        jobTypeService.save(jobType);
        return new ResponseEntity<>(jobType, HttpStatus.CREATED);
    }

    // Cập nhật một JobType
    @PutMapping("/{id}")
    public ResponseEntity<JobType> updateJobType(@PathVariable Long id, @RequestBody JobType jobType) {
        Optional<JobType> existingJobType = jobTypeService.findById(id);
        if (!existingJobType.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobType.setId(id);
        jobTypeService.save(jobType);
        return new ResponseEntity<>(jobType, HttpStatus.OK);
    }

    // Xóa một JobType dựa trên id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteJobType(@PathVariable Long id) {
        Optional<JobType> jobType = jobTypeService.findById(id);
        if (!jobType.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobTypeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}