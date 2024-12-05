package com.example.demo.service.Department;

import com.example.demo.dto.*;
import com.example.demo.dto.Summary.DepartmentSummaryDTO;
import com.example.demo.dto.Summary.DepartmentSummaryDTO3;
import com.example.demo.dto.Summary.JobTypeSummaryDTO;
import com.example.demo.dto.Summary.ProjectSummaryDTO3;
import com.example.demo.model.*;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IJobTypeRepository;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class DepartmentService implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;
    private final IJobTypeRepository jobTypeRepository;

    //get all department
    @Override
    public List<DepartmentDTO> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map((department) -> mapToDepartmentDTO(department)).collect(Collectors.toList());
    }

    //mapper
    public DepartmentDTO mapToDepartmentDTO(Department department) {
        //map list jobtypes to jobTypeDtos
        Set<JobTypeDTO> jobTypeDTOs = department.getJobTypes().stream()
                .map((jobTypes) -> new JobTypeDTO(jobTypes.getId(), jobTypes.getName()))
                .collect(Collectors.toSet());

        //map list users to userDtos
        Set<UserDTO> userDTOs = department.getUsers().stream()
                .map((users) -> new UserDTO(users.getId(), users.getUserName(), users.getFullName()))
                .collect(Collectors.toSet());

        //map department to departmentDto
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .jobTypes(jobTypeDTOs)
                .users(userDTOs)
                .build();
        return departmentDTO;
    }

    //Save Department
    public Department saveDepartment(Department department){
        return departmentRepository.save(department);
    }

    //Find by Id
    @Override
    public DepartmentDTO findById(long departmentId) {
        Department department = departmentRepository.findById(departmentId).get();
        return mapToDepartmentDTO(department);
    }

    //Update
    @Override
    public void updateDepartment(DepartmentDTO departmentDto) {
        Department department = mapToDepartment(departmentDto);
        departmentRepository.save(department);
    }

    //Map to edit
    private Department mapToDepartment(DepartmentDTO departmentDto){
        Department department = Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .build();

        return department;
    }

    //Delete
    @Override
    public void delete(long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    //Show list job type
    @Override
    public Set<JobType> findJobTypesByDepartment(Long departmentId) {
        return departmentRepository.findJobTypesByDepartment(departmentId);
    }

    //show list user
    @Override
    public Set<User> findUsersByDepartment(Long departmentId) {
        return departmentRepository.findUsersByDepartment(departmentId);
    }

    //Edit (OLD)
    @Transactional
    public DepartmentDTO editDepartment(Long departmentId, String newName, Set<Long> newJobTypeIds) {
        // Find the department by ID
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (!optionalDepartment.isPresent()) {
            throw new NoSuchElementException("Department not found with ID: " + departmentId);
        }

        Department department = optionalDepartment.get();

        // Update the department's name
        if (newName != null && !newName.trim().isEmpty()) {
            department.setName(newName);
        }

        // Update the department's job types
        if (newJobTypeIds != null && !newJobTypeIds.isEmpty()) {
            // Fetch the new JobType entities by their IDs
            List<JobType> newJobTypes = jobTypeRepository.findAllById(newJobTypeIds);
            if (newJobTypes.size() != newJobTypeIds.size()) {
                throw new IllegalArgumentException("One or more JobType IDs are invalid.");
            }
            department.setJobTypes(new HashSet<>(newJobTypes));
        }

        // Save the updated department
        Department updatedDepartment = departmentRepository.save(department);

        // Map the updated department to DepartmentDTO
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(updatedDepartment.getId());
        departmentDTO.setName(updatedDepartment.getName());

        // Map job types to JobTypeDTO
        Set<JobTypeDTO> jobTypeDTOS = new HashSet<>();
        for (JobType jobType : updatedDepartment.getJobTypes()) {
            JobTypeDTO jobTypeDTO = new JobTypeDTO();
            jobTypeDTO.setId(jobType.getId());
            jobTypeDTO.setName(jobType.getName());
            jobTypeDTOS.add(jobTypeDTO);
        }
        departmentDTO.setJobTypes(jobTypeDTOS);

        return departmentDTO;
    }

    //Summarize JobType by Department
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

    //Summarize Pj by department
    public List<DepartmentSummaryDTO3> getSummaryByDepartment3() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentSummaryDTO3> summaries = new ArrayList<>();

        for (Department department : departments) {
            DepartmentSummaryDTO3 departmentSummary3 = new DepartmentSummaryDTO3();
            departmentSummary3.setName(department.getName());

            // Map to accumulate total time for each JobType within the department
            Map<String, Float> projectTotalTimeMap = new HashMap<>();

            // For each user in the department, retrieve all their tasks and calculate total times per JobType
            for (User user : department.getUsers()) {
                for (WorkTime workTime : user.getWorkTimes()) {
                    for (Task task : workTime.getTasks()) {
                        Project project = task.getProject();
                        if (project != null) {
                            String projectName = project.getName();
                            float currentTotal = projectTotalTimeMap.getOrDefault(projectName, 0f);
                            projectTotalTimeMap.put(projectName, currentTotal + task.getTotalTime());
                        }
                    }
                }
            }

            // Convert the project map to a list of ProjectSummaryDTO3
            List<ProjectSummaryDTO3> projectSummaries = projectTotalTimeMap.entrySet()
                    .stream()
                    .map(entry -> {
                        ProjectSummaryDTO3 projectSummary = new ProjectSummaryDTO3();
                        projectSummary.setName(entry.getKey());
                        projectSummary.setTotalTime(entry.getValue());
                        return projectSummary;
                    })
                    .collect(Collectors.toList());

            departmentSummary3.setProjectSummaries(projectSummaries);
            summaries.add(departmentSummary3);
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

 /* //Don't care about this
    @Override
    public void delete(User user) {
    }


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
*/



}

