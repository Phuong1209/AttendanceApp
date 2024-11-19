package com.example.demo.service;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.ProjectSummaryDTO;
import com.example.demo.model.Project;
import com.example.demo.model.Task;

import java.util.List;

public interface IProjectService extends IGeneralService<Project>{
    List<ProjectDTO> getAllProject();
    List<Task> getTaskByProject(Long projectId);
    List<ProjectSummaryDTO> getSummaryByProject();

}
