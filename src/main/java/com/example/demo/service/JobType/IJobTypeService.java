package com.example.demo.service.JobType;
import com.example.demo.dto.JobTypeDTO;
import com.example.demo.model.JobType;
import com.example.demo.service.IGeneralService;

import java.util.Optional;


public interface IJobTypeService extends IGeneralService<JobType> {
 /*   Iterable<JobType> findAll();
    JobType save(JobType jobType); // Add this method if not already present

    Optional<JobType> findById(Long id);
    void deleteById(Long id);

    void save(JobTypeDTO jobTypeDTO);
}*/
 Iterable<JobType> findAll();
    Optional<JobType> findById(Long id); // Fetch DTO by ID
    void save(JobTypeDTO jobTypeDTO); // Save DTO
    /*void deleteById(Long id); // Delete by ID*/
    void remove(Long id);
}

