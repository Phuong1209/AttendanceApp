package com.example.demo.service.JobType;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.JobTypeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.repository.IJobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public void delete(User user) {

    }

}