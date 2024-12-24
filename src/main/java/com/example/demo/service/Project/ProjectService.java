//Thu Phuong

package com.example.demo.service.Project;

import com.example.demo.dto.Summary.JobTypeSummaryDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.Summary.ProjJobSummaryDTO;
import com.example.demo.dto.Summary.ProjectSummaryDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
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

    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;

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
    public Project save(Project model) { return projectRepository.save(model);}

    @Transactional
    @Override
    public void remove(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {

    }

    //get list task of project
    public List<Task> getTaskByProject(Long projectId) {
        if(projectId != null){
            Optional<Project>optionalProject = projectRepository.findById(projectId);
            if(optionalProject.isPresent()) {
                Project foundProject = optionalProject.get();
                List<Task> tasks = taskRepository.findByProject(foundProject);
                log.info("Tasks of project {}:{}",foundProject.getName(),tasks);
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
        for(Project project : projects) {
            ProjectDTO projectDTO = ProjectDTO.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .code(project.getCode())
                    .build();
//            ProjectDTO projectDTO = new ProjectDTO();
//            projectDTO.setId(project.getId());
//            projectDTO.setName(project.getName());
//            projectDTO.setCode(project.getCode());

            //get task list
            List<Task> tasks = taskRepository.findByProject(project);
            Set<TaskDTO> taskDTOS = new HashSet<>();
            for(Task task : tasks) {
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

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    //Edit
    @Transactional
    public ProjectDTO editProject(Long projectId, String newName, String newCode,Set<Long> newTaskIds) {
        // Find the project by ID
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            throw new NoSuchElementException("Project not found with ID: " + projectId);
        }

        Project project = optionalProject.get();

        // Update project's name
        if (newName != null && !newName.trim().isEmpty()) {
            project.setName(newName);
        }

        // Update project's code
        if (newCode != null && !newCode.trim().isEmpty()) {
            project.setCode(newCode);
        }

        // Update the project's tasks
        if (newTaskIds != null && !newTaskIds.isEmpty()) {
            // Fetch the new Task entities by their IDs
            List<Task> newTasks = taskRepository.findAllById(newTaskIds);
            if (newTasks.size() != newTaskIds.size()) {
                throw new IllegalArgumentException("One or more JobType IDs are invalid.");
            }
            project.setTasks(new HashSet<>(newTasks));
        }

        // Save the updated project
        Project updatedProject = projectRepository.save(project);

        // Map the updated department to DepartmentDTO
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .build();
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setId(updatedProject.getId());
//        projectDTO.setName(updatedProject.getName());
//        projectDTO.setCode(updatedProject.getCode());

        // Map tasks to TaskDTO
        Set<TaskDTO> taskDTOS = new HashSet<>();
        for (Task task : updatedProject.getTasks()) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTotalTime(task.getTotalTime());
            taskDTO.setComment(task.getComment());
            taskDTOS.add(taskDTO);
        }
        projectDTO.setTasks(taskDTOS);

        return projectDTO;
    }

    @Override
    public ProjectDTO findProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        return mapToProjectDTO(project);
    }

    @Override
    public void updateProject(ProjectDTO projectDTO) {
        Project project = mapToProject(projectDTO);
        projectRepository.save(project);
    }

    @Override
    public void deleteByProjectId(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    public boolean canDeleteProject(Long projectId) {
        return taskRepository.findByProjectId(projectId).isEmpty();
    }

    public static Project mapToProject(ProjectDTO project) {
        Project projectDTO = Project.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .build();
        return  projectDTO;
    }

    public static ProjectDTO mapToProjectDTO(Project project) {
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .build();
        return projectDTO;
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
                JobType jobType = task.getJobType();
                if (jobType != null) {
                    String jobTypeName = jobType.getName();
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

    //Summarize by Project FOR UI
    public List<ProjJobSummaryDTO> getProjJobSummary() {
        List<ProjJobSummaryDTO> summaries = new ArrayList<>();
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects) {
//            ProjJobSummaryDTO projJobSummaryDTO = new ProjJobSummaryDTO();
//            projJobSummaryDTO.setProjectName(project.getName());
//            projJobSummaryDTO.setProjectCode(project.getCode());

            // Map to accumulate total time for each JobType within the project
            Map<String, Float> jobTypeTotalTimeMap = new HashMap<>();

            // For each project, retrieve all the tasks and calculate total times per JobType
            for (Task task : project.getTasks()) {
                JobType jobType = task.getJobType();
                if (jobType != null) {
                    String jobTypeName = jobType.getName();
                    float currentTotal = jobTypeTotalTimeMap.getOrDefault(jobTypeName, 0f);
                    jobTypeTotalTimeMap.put(jobTypeName, currentTotal + task.getTotalTime());
                }
            }

            // Create and add a new ProjJobSummaryDTO for each job type
            for (Map.Entry<String, Float> entry : jobTypeTotalTimeMap.entrySet()) {
                ProjJobSummaryDTO projJobSummaryDTO = new ProjJobSummaryDTO();
                projJobSummaryDTO.setProjectName(project.getName());
                projJobSummaryDTO.setProjectCode(project.getCode());
                projJobSummaryDTO.setJobTypeName(entry.getKey());
                projJobSummaryDTO.setTotalTime(entry.getValue());
                summaries.add(projJobSummaryDTO);
            }
            // Convert the job type map to a list of JobTypeSummaryDTO
//            List<ProjJobSummaryDTO> jobTypeSummaries = jobTypeTotalTimeMap.entrySet()
//                    .stream()
//                    .map(entry -> {
////                        ProjJobSummaryDTO projJobSummaryDTO = new ProjJobSummaryDTO();
//                        projJobSummaryDTO.setJobTypeName(entry.getKey());
//                        projJobSummaryDTO.setTotalTime(entry.getValue());
//                        return projJobSummaryDTO;
//                    })
//                    .collect(Collectors.toList());

//            projectSummary.setJobTypeSummaries(jobTypeSummaries);
//            summaries.add(projJobSummaryDTO);
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
