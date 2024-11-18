package com.example.demo.service.JobType;

import com.example.demo.model.JobType;
import com.example.demo.model.dto.JobTypeDTO;
import com.example.demo.repository.IJobTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobTypeService implements IJobTypeService {
    @Autowired
    private IJobTypeRepository jobTypeRepository;

    @Override
    public Iterable<JobType> findAll() {
        return jobTypeRepository.findAll();
    }

    @Override
    public Optional<JobType> findById(Long id) {
        return jobTypeRepository.findById(id);
    }

    @Override
    public JobType save(JobType model) {
        return jobTypeRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        jobTypeRepository.deleteById(id);
    }

   /* //Edit
    @Transactional
    public JobTypeDTO editJobType(Long jobTypeId, String newName) {
        // Find the jobType by ID
        Optional<JobType> optionalJobType = jobTypeRepository.findById(jobTypeId);
        if (!optionalJobType.isPresent()) {
            throw new NoSuchElementException("JobType not found with ID: " + jobTypeId);
        }

        JobType jobType = optionalJobType.get();

        // Update the jobType's name
        if (newName != null && !newName.trim().isEmpty()) {
            jobType.setName(newName);
        }

        // Save the updated jobType
        JobType updatedJobType = jobTypeRepository.save(jobType);

        // Map the updated department to DepartmentDTO
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        jobTypeDTO.setId(updatedJobType.getId());
        jobTypeDTO.setName(updatedJobType.getName());

        return jobTypeDTO;
    }*/

}