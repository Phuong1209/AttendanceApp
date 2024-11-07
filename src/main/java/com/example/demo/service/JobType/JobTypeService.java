package com.example.demo.service.JobType;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IJobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
}