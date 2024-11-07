package com.example.demo.service;

import com.example.demo.model.JobType;
import com.example.demo.repository.IJobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobTypeService implements IJobTypeService {
    @Autowired
    private IJobTypeRepository JobTypeRepository;

    @Override
    public List<JobType> findAll() {
        return JobTypeRepository.findAll();  // Sử dụng repository để lấy tất cả JobType
    }
    // 1101 added from now is code testing
    // Method to add a new job type
    public JobType addJobType(String jobTypeName) {
        JobType jobType = new JobType();
        jobType.setJobTypeName(jobTypeName);
        jobType.setCreate_at(LocalDateTime.now());
        jobType.setUpdate_at(LocalDateTime.now());

        return JobTypeRepository.save(jobType);
    }
    public JobType updateJobTypeName(Long id, String jobTypeName) {
        Optional<JobType> jobTypeOptional = JobTypeRepository.findById(id);
        if (jobTypeOptional.isPresent()) {
            JobType jobType = jobTypeOptional.get();
            jobType.setJobTypeName(jobTypeName); // Set the new name
            return JobTypeRepository.save(jobType); // Save the updated object
        } else {
            throw new RuntimeException("JobType not found with id: " + id);
        }
    }
// end code test here

    @Override
    public Optional<JobType> findById(Long id) {
        return JobTypeRepository.findById(id);  // Tìm JobType theo id
    }

    @Override
    public void save(JobType jobType) {
        JobTypeRepository.save(jobType);  // Lưu JobType vào database
    }

    @Override
    public void remove(Long id) {
        JobTypeRepository.deleteById(id);  // Xóa JobType theo id
    }
}