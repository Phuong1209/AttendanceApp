package com.example.demo.service.Project;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.Summary.ProjJobSummaryDTO;
import com.example.demo.dto.Summary.ProjectSummaryDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.service.IGeneralService;

import java.util.List;
import java.util.Set;

public interface IProjectService extends IGeneralService<Project> {
    List<ProjectDTO> getAllProject();
    Project saveProject(Project project);

    List<Task> getTaskByProject(Long projectId);
    List<ProjectSummaryDTO> getSummaryByProject();
    //edit
    ProjectDTO editProject(Long projectId, String name, String code, Set<Long> taskIds);

    ProjectDTO findProjectById(Long projectId);

    void updateProject(ProjectDTO project);

    void deleteByProjectId(Long projectId);

    List<ProjJobSummaryDTO> getProjJobSummary();

    boolean canDeleteProject(Long projectId);
}
