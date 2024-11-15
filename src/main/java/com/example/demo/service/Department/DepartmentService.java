package com.example.demo.service.Department;

import com.example.demo.model.*;
import com.example.demo.model.dto.*;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class DepartmentService implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;
    private final IUserRepository userRepository;
    private final IJobTypeRepository jobTypeRepository;

    @Transactional
    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Transactional
    @Override
    public Department save(Department model) {
        return departmentRepository.save(model);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        departmentRepository.deleteById(id);
    }

    //get list user of department
    public List<User> getUserByDepartment(Long departmentId) {
        if(departmentId != null){
            Optional<Department>optionalDepartment = departmentRepository.findById(departmentId);
            if(optionalDepartment.isPresent()) {
                Department foundDepartment=optionalDepartment.get();
                List<User>users = userRepository.findByDepartments(foundDepartment);
                log.info("Users of department {}:{}",foundDepartment.getName(),users);
                return users;
            }
        }
        return Collections.emptyList();
    }

    //get list jobType of department
    public List<JobType> getJobTypeByDepartment(Long departmentId) {
        if(departmentId != null){
            Optional<Department>optionalDepartment = departmentRepository.findById(departmentId);
            if(optionalDepartment.isPresent()) {
                Department foundDepartment=optionalDepartment.get();
                List<JobType> jobTypes = jobTypeRepository.findByDepartments(foundDepartment);
                log.info("JobTypes of department {}:{}",foundDepartment.getName(), jobTypes);
                return jobTypes;
            }
        }
        return Collections.emptyList();
    }

    //get list department
    @Override
    public List<DepartmentDTO> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();
        for(Department department : departments) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setName(department.getName());
            departmentDTO.setId(department.getId());

            //get user list
            List<User> users = userRepository.findByDepartments(department);
            Set<UserDTO> userDTOS = new HashSet<>();
            for(User user: users) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUserName(user.getUserName());
                userDTO.setFullName(user.getFullName());
                userDTO.setPassword(user.getPassword());
                userDTOS.add(userDTO);
            }
            //add user list to department
            departmentDTO.setUsers(userDTOS);

            //get jobType list
            List<JobType> jobTypes = jobTypeRepository.findByDepartments(department);
            Set<JobTypeDTO> jobTypeDTOS = new HashSet<>();
            for(JobType jobType : jobTypes) {
                JobTypeDTO jobTypeDTO = new JobTypeDTO();
                jobTypeDTO.setId(jobType.getId());
                jobTypeDTO.setName(jobType.getName());
                jobTypeDTOS.add(jobTypeDTO);
            }
            //add user list to department
            departmentDTO.setJobTypes(jobTypeDTOS);

            //add department to list department
            departmentDTOS.add(departmentDTO);
        }
        return departmentDTOS;
    }

    //Summarize by Department
    public List<DepartmentSummaryDTO> getSummaryByDepartment() {
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

