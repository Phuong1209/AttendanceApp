package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.service.JobType.IJobTypeService;
import com.example.demo.service.Project.IProjectService;
import com.example.demo.service.Task.ITaskService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/task")

public class TaskController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IWorkTimeService workTimeService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IJobTypeService jobTypeService;

    //show list
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> taskDTOS = taskService.getAllTask();
        return ResponseEntity.ok(taskDTOS);
    }

    //show by id (new)
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        if (taskDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        return ResponseEntity.ok(taskDTO);
    }

    //create (new)
    @PostMapping("")
    public ResponseEntity<?> createTask(@RequestBody Map<String, Object> requestBody) {
        try {
            // Parse request body
            Float totalTime = Float.parseFloat(requestBody.get("totalTime").toString());
            String comment = String.valueOf(requestBody.get("comment").toString());

            Map<String, Object> workTimeMap = (Map<String, Object>) requestBody.get("workTime");
            Long workTimeId = Long.valueOf(workTimeMap.get("id").toString());

            Map<String, Object> projectMap = (Map<String, Object>) requestBody.get("project");
            Long projectId = Long.valueOf(projectMap.get("id").toString());

            Map<String, Object> jobTypeMap = (Map<String, Object>) requestBody.get("jobType");
            Long jobTypeId = Long.valueOf(jobTypeMap.get("id").toString());

            // Fetch WorkTime entity
            Optional<WorkTime> optionalWorkTime = workTimeService.findById(workTimeId);
            if (optionalWorkTime.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WorkTime not found.");
            }
            WorkTime workTime = optionalWorkTime.get();
            User currentUser = workTime.getUser();
            Set<Department> departments = currentUser.getDepartments();

            // Check task count for the date
            LocalDate workDate = workTime.getDate();
            int taskCount = taskService.countByWorkTimeAndDate(workTimeId, workDate);
            if (taskCount >20) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task limit for the day exceeded.");
            }

            // Fetch Project entity
            Optional<Project> optionalProject = projectService.findById(projectId);
            if (optionalProject.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
            }
            Project project = optionalProject.get();

            // Fetch JobType entity
            Optional<JobType> optionalJobType = jobTypeService.findById(jobTypeId);
            if (optionalJobType.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JobType not found.");
            }
            JobType jobType = optionalJobType.get();

            boolean isAllowed = departments.stream()
                    .flatMap(department -> department.getJobTypes().stream())
                    .anyMatch(JobType->JobType.getId().equals(jobType.getId()));

            if (!isAllowed){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Selected JobType is not allowed for the user's departments");
            }

            // Create and save Task
            Task task = Task.builder()
                    .totalTime(totalTime)
                    .comment(comment)
                    .workTime(workTime)
                    .project(project)
                    .jobType(jobType)
                    .build();
            Task savedTask = taskService.save(task);

            // Construct response body
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedTask.getId());
            response.put("totalTime", savedTask.getTotalTime());
            response.put("comment", savedTask.getComment().toString());
            response.put("workTime", Map.of("id", workTime.getId()));
            response.put("project", Map.of("id", project.getId()));
            response.put("jobType", Map.of("id", jobType.getId()));

            // Return 201 Created with response body
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            // Handle exceptions gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    //create (old)
/*
    @PostMapping("")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        taskService.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }
*/

    //edit (new)
    @PutMapping("/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            // Fetch the existing WorkTime record
            Optional<Task> optionalTask = taskService.findById(id);
            if (optionalTask.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
            }
            Task existingTask = optionalTask.get();

            // Parse request body for editable fields
            if (requestBody.containsKey("totalTime")) {
                existingTask.setTotalTime(Float.parseFloat(requestBody.get("totalTime").toString()));
            }
            if (requestBody.containsKey("comment")) {
                existingTask.setComment(String.valueOf((String) requestBody.get("comment")));
            }

            // Save updated Task
            Task updatedTask = taskService.save(existingTask);

            // Map Task to DTO
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(updatedTask.getId());
            taskDTO.setTotalTime(updatedTask.getTotalTime());
            taskDTO.setComment(updatedTask.getComment());

            // Return response
            return ResponseEntity.ok(taskDTO);

        } catch (Exception e) {
            // Handle exceptions gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
