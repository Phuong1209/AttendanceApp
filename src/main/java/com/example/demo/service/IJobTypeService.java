package com.example.demo.service;
import com.example.demo.model.JobType;
import java.util.List;
import java.util.Optional;

public interface IJobTypeService {
    List<JobType> findAll();  // Phải trả về List
    Optional<JobType> findById(Long id);
    void save(JobType jobType);
    void remove(Long id);
///1101 added
    JobType addJobType(String jobTypeName);

    JobType updateJobTypeName(Long id, String jobTypeName);
    ///
}