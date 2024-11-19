package com.example.demo.service;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.DepartmentSummaryDTO;
import com.example.demo.dto.JobTypeSummaryDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.*;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.MemberManagementRepository;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MemberManagementRepository memberManagementRepository;
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

    @Override
    public Object save(Object model) {
        return null;
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
                List<User>users = memberManagementRepository.findByDepartments(foundDepartment);
                log.info("Users of department {}:{}",foundDepartment.getDepartmentName(),users);
                return users;
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
            departmentDTO.setDepartmentName(department.getDepartmentName());
            departmentDTO.setId(department.getId());

            //get user list
            List<User> users = memberManagementRepository.findByDepartments(department);
            Set<UserDTO> userDTOS = new HashSet<>();
            for(User user: users) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUserName(user.getUserName());
                userDTO.setUserFullName(user.getUserFullName());
                userDTO.setPassword(user.getUserPasswords());
                userDTOS.add(userDTO);
            }
            //add user list to department
            departmentDTO.setUsers(userDTOS);

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
            departmentSummary.setName(department.getDepartmentName());

            // Map to accumulate total time for each JobType within the department
            Map<String, Float> jobTypeTotalTimeMap = new HashMap<>();

            // For each user in the department, retrieve all their tasks and calculate total times per JobType
            for (User user : department.getUsers()) {
                for (WorkingTime workTime : user.getWorkingTimes()) {
                    for (Task task : workTime.getTasks()) {
                        JobType jobType = task.getJobtype();
                        if (jobType != null) {
                            String jobTypeName = jobType.getJobTypeName();
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
    // Phương thức xuất CSV cho DepartmentSummaryDTO
    public void exportDepartmentSummaryToCSV(HttpServletResponse response, List<DepartmentSummaryDTO> summaries) throws IOException {
        // Cài đặt loại nội dung và tên tệp CSV cho phản hồi HTTP
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=department_summary.csv");

        // Tạo đối tượng CSVWriter để ghi vào response
        CSVWriter writer = new CSVWriter(response.getWriter());

        // Viết tiêu đề cột vào file CSV
        writer.writeNext(new String[] {"Department Name", "Job Type", "Total Time"});

        // Duyệt qua danh sách summaries và thêm dữ liệu vào file CSV
        for (DepartmentSummaryDTO departmentSummary : summaries) {
            String departmentName = departmentSummary.getName();

            // Duyệt qua danh sách JobTypeSummaryDTO trong DepartmentSummaryDTO
            for (JobTypeSummaryDTO jobTypeSummary : departmentSummary.getJobTypeSummaries()) {
                String jobTypeName = jobTypeSummary.getName();
                Float totalTime = jobTypeSummary.getTotalTime();

                // Ghi dữ liệu vào file CSV
                writer.writeNext(new String[] {departmentName, jobTypeName, String.valueOf(totalTime)});
            }
        }
        // Đóng CSVWriter sau khi hoàn thành
        writer.close();
    }
}