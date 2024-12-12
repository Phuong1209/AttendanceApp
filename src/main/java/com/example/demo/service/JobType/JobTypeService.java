package com.example.demo.service.JobType;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.JobTypeDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.Project;
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

    @Override
    public List<JobTypeDTO> getAllJobType() {
        List<JobType> jobTypes = jobTypeRepository.findAll();
//        return jobTypes.stream().map((jobType) -> mapToJobTypeDTO(jobType)).collect(Collectors.toList());
        return null;
    }

    public static JobTypeDTO mapToJobTypeDTO(Project project) {
        JobTypeDTO jobTypeDTO = JobTypeDTO.builder()
                .id(project.getId())
                .name(project.getName())
//                .code(project.getCode())
                .build();
        return jobTypeDTO;
    }

    public static ProjectDTO mapToProjectDTO(Project project) {
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .build();
        return projectDTO;
    }
}