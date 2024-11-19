package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.dto.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.service.Project.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/project")

public class ProjectController {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private ITaskRepository taskRepository;

    //show list
    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok().body(projectService.findAll());
    }

    //show by id
    @GetMapping("/{id}")
    public ResponseEntity<Project> getAllProjectById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        return projectOptional.map(project -> new ResponseEntity<>(project, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("getTask/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        List<Task> tasks = projectService.getTaskByProject(id);
        return ResponseEntity.ok().body(tasks);
    }

    //create (new)
    @PostMapping("")
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO projectDTO) {
        Project newProject = new Project();
        newProject.setName(projectDTO.getName());
        newProject.setCode(projectDTO.getCode());

        //save new project
        projectService.save(newProject);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    //create (old)
    /*
    @PostMapping("")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        projectService.save(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }
*/

    //edit (old)
/*    @PutMapping("/{id}")
    public ResponseEntity<Project> editProject(@PathVariable Long id, @RequestBody Project project) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        project.setId(id);
        projectService.save(project);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }*/

    //edit (new)
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> editProject(
            @PathVariable("id") Long projectId,
            @RequestBody ProjectEditRequest editRequest) {
        ProjectDTO updatedProject = projectService.editProject(projectId, editRequest.getName(), editRequest.getCode(), editRequest.getTaskIds());
        return ResponseEntity.ok(updatedProject);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //summarize
    @GetMapping("/summarize")
    public ResponseEntity<List<ProjectSummaryDTO>> getSummaryByProject() {
        List<ProjectSummaryDTO> summaries = projectService.getSummaryByProject();
        return ResponseEntity.ok(summaries);
    }

}