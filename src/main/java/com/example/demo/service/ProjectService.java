package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.JobType;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class ProjectService implements IProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    //CRUD
    @Transactional
    @Override
    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Transactional
    @Override
    public Project save(Project model) {
        return projectRepository.save(model);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        projectRepository.deleteById(id);
    }

    //get list task of project
    public List<Task> getTaskByProject(Long projectId) {
        if (projectId != null) {
            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (optionalProject.isPresent()) {
                Project foundProject = optionalProject.get();
                List<Task> tasks = taskRepository.findByProject(foundProject);
                log.info("Tasks of project {}:{}", foundProject.getName(), tasks);
                return tasks;
            }
        }
        return Collections.emptyList();
    }

    //get list Project
    @Override
    public List<ProjectDTO> getAllProject() {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(project.getId());
            projectDTO.setName(project.getName());
            projectDTO.setCode(project.getCode());

            //get task list
            List<Task> tasks = taskRepository.findByProject(project);
            Set<TaskDTO> taskDTOS = new HashSet<>();
            for (Task task : tasks) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTotalTime(task.getTotalTime());
                taskDTO.setComment(task.getComment());
                taskDTOS.add(taskDTO);
            }
            //add task list to project
            projectDTO.setTasks(taskDTOS);

            //add department to list department
            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }

    //Summarize by Project
    public List<ProjectSummaryDTO> getSummaryByProject() {
        List<ProjectSummaryDTO> summaries = new ArrayList<>();
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects) {
            ProjectSummaryDTO projectSummary = new ProjectSummaryDTO();
            projectSummary.setName(project.getName());

            // Map to accumulate total time for each JobType within the project
            Map<String, Float> jobTypeTotalTimeMap = new HashMap<>();

            // For each project, retrieve all the tasks and calculate total times per JobType
            for (Task task : project.getTasks()) {
                JobType jobType = task.getJobtype();
                if (jobType != null) {
                    String jobTypeName = jobType.getJobTypeName();
                    float currentTotal = jobTypeTotalTimeMap.getOrDefault(jobTypeName, 0f);
                    jobTypeTotalTimeMap.put(jobTypeName, currentTotal + task.getTotalTime());
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

            projectSummary.setJobTypeSummaries(jobTypeSummaries);
            summaries.add(projectSummary);
        }
        return summaries;
    }

    // Phương thức xuất CSV cho ProjectSummaryDTO
    public void exportProjectSummaryToCSV(HttpServletResponse response, List<ProjectSummaryDTO> summaries) throws IOException {
        // Cài đặt loại nội dung và tên tệp CSV cho phản hồi HTTP
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=project_summary.csv");

        // Tạo đối tượng CSVWriter để ghi vào response
        CSVWriter writer = new CSVWriter(response.getWriter());

        // Viết tiêu đề cột vào file CSV
        writer.writeNext(new String[] {"Project Name", "Job Type", "Total Time"});

        // Duyệt qua danh sách summaries và thêm dữ liệu vào file CSV
        for (ProjectSummaryDTO projectSummary : summaries) {
            String projectName = projectSummary.getName();

            // Duyệt qua danh sách JobTypeSummaryDTO trong DepartmentSummaryDTO
            for (JobTypeSummaryDTO jobTypeSummary : projectSummary.getJobTypeSummaries()) {
                String jobTypeName = jobTypeSummary.getName();
                Float totalTime = jobTypeSummary.getTotalTime();

                // Ghi dữ liệu vào file CSV
                writer.writeNext(new String[] {projectName, jobTypeName, String.valueOf(totalTime)});
            }
        }
        // Đóng CSVWriter sau khi hoàn thành
        writer.close();
    }
}

