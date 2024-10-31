package com.example.demo.service;

import com.example.demo.model.JobType;
import java.util.List;
import java.util.Optional;

public interface IJobTypeService {
    List<JobType> findAll();
    Optional<JobType> findById(Long id);
    void save(JobType jobType);
    void remove(Long Jobtypeid);
}
