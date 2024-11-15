package com.example.demo.service.Project;

import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.dto.ProjectDTO;
import com.example.demo.model.dto.ProjectSummaryDTO;
import com.example.demo.service.IGeneralService;

import java.util.List;

public interface IProjectService extends IGeneralService<Project> {
    List<ProjectDTO> getAllProject();
    List<Task> getTaskByProject(Long projectId);
    List<ProjectSummaryDTO> getSummaryByProject();

}
