package com.example.demo.service.Department;

import com.example.demo.model.*;
import com.example.demo.model.dto.*;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<DepartmentDTO> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();
        for(Department department : departments) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setName(department.getName());
            departmentDTO.setId(department.getId());

            //get department list by user and set to department for user
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
            departmentDTO.setUsers(userDTOS);

            departmentDTOS.add(departmentDTO);
        }
        return departmentDTOS;
    }

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

    //test DTO
    /*@Transactional
    public List<DepartmentDTO> getAllDepartmentsWithUsers() {
        List<Department> departments = departmentRepository.findAll();
        departments.forEach(department -> Hibernate.initialize(department.getUsers()));
        return departments.stream().map(department -> {
            Set<UserDTO> userDTOs = department.getUsers().stream()
                    .map(user -> new UserDTO(user.getId(), user.getFullName(), user.getUserName(), user.getPassword()))
                    .collect(Collectors.toSet());
            return new DepartmentDTO(department.getId(), department.getName(), userDTOs);
        }).collect(Collectors.toList());
    }

    //test
    @Transactional
    public Department addUsersToDepartment(Long departmentId, Set<User> users) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        for (User user : users) {
            user.getDepartments().add(department);
            department.getUsers().add(user);
            userRepository.save(user);
        }

        return departmentRepository.save(department);
    }
*/
/*    public List<DepartmentDTO> getAllDepartmentsWithUsers() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDtos = new ArrayList<>();
        for(Department department : departments){
            DepartmentDTO departmentDto = new DepartmentDTO();
            departmentDto.setName(department.getName());
            departmentDto.setId(department.getId());

            //get department's user list
            //add userDto to list userDtos
            List<User> users = userRepository.findByDepartments(department);
            Set<UserDTO> userDtos = new HashSet<>();
            for(User user : users){
                UserDTO userDto = new UserDTO();
                userDto.setUserName(user.getUserName());
                userDto.setFullName(user.getFullName());
                userDto.setPassword(user.getPassword());
                userDtos.add(userDto);
            }
            //set userDtos to departmentDto
            departmentDto.setUsers(userDtos);

            //add DepartmentDto to Dtos
            departmentDtos.add(departmentDto);
        }
        return departmentDtos;
    }*/

    /*public List<JobType>getJobTypeByDepartment(Long departmentId){
        if(departmentId!=null){
            Optional<Department>optionalDepartment=departmentRepository.findById(departmentId);
            if(optionalDepartment.isPresent()){
                Department foundDepartment=optionalDepartment.get();
                List<JobType>jobTypes=jobTypeRepository.findByDepartments(foundDepartment);
                log.info("JobTypes of department {}:{}",foundDepartment.getName(),jobTypes);
                return jobTypes;
            }
        }
        return Collections.emptyList();
    }
    public List<User>getUserByDepartment(Long departmentId) {
        if (departmentId != null) {
            Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
            if (optionalDepartment.isPresent()) {
                Department foundDepartment = optionalDepartment.get();
                List<User> users = userRepository.findByDepartments(foundDepartment);
                log.info("Users of department {}:{}", foundDepartment.getName(), users);
                return users;
            }
        }
        return Collections.emptyList();
    }*/

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

