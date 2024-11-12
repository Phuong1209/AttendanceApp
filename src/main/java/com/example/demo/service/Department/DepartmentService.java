package com.example.demo.service.Department;

import com.example.demo.model.*;
import com.example.demo.model.dto.DepartmentSummaryDTO;
import com.example.demo.model.dto.JobTypeSummaryDTO;
import com.example.demo.repository.IDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }
    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department save(Department model) {
        return departmentRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        departmentRepository.deleteById(id);
    }

    //Summarize Department
    public List<DepartmentSummaryDTO> getDepartmentSummaries() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentSummaryDTO> summaries = new ArrayList<>();

        for (Department department : departments) {
            DepartmentSummaryDTO departmentSummary = new DepartmentSummaryDTO();
            departmentSummary.setName(department.getName());

            // Map to accumulate total time for each JobType within the department
            Map<String, Float> jobTypeTotalTimeMap = new HashMap<>();

            // For each user in the department, retrieve all their tasks and calculate total times per JobType
            for (User user : department.getUsers()) {
                for (WorkTime workTime : user.getWorkTimes()) {
                    for (Task task : workTime.getTasks()) {
                        JobType jobType = task.getJobType();
                        if (jobType != null) {
                            String jobTypeName = jobType.getName();
                            float currentTotal = jobTypeTotalTimeMap.getOrDefault(jobTypeName, 0f);
                            jobTypeTotalTimeMap.put(jobTypeName, currentTotal + task.getTotalTime());
                        }
                    }
                }
            }

            // Convert the job type map to a list of JobTypeSummaryDTO
            List<JobTypeSummaryDTO> jobTypeSummaries = jobTypeTotalTimeMap.entrySet()
                    .stream()
                    .map(entry -> {
                        JobTypeSummaryDTO jobTypeSummary = new JobTypeSummaryDTO();
                        jobTypeSummary.setName(entry.getKey());
                        jobTypeSummary.setTotalTime(entry.getValue());
                        return jobTypeSummary;
                    })
                    .collect(Collectors.toList());

            departmentSummary.setJobTypeSummaries(jobTypeSummaries);
            summaries.add(departmentSummary);
        }
        return summaries;
    }


}

