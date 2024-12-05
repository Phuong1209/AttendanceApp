package com.example.demo.service.Task;

import com.example.demo.dto.*;
import com.example.demo.model.Department;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.ITaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final ITaskRepository taskRepository;

    //get all
    @Override
    public List<TaskDTO> getAllTask() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task) -> mapToTaskDTO(task)).collect(Collectors.toList());
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    //mapper
    public TaskDTO mapToTaskDTO(Task task) {
        //jobtype --> jobtypeDto
        JobTypeDTO jobTypeDTO = JobTypeDTO.builder()
                .id(task.getJobType().getId())
                .name(task.getJobType().getName())
                .build();

        //project --> projectDto
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(task.getProject().getId())
                .name(task.getProject().getName())
                .build();

        TaskDTO taskDTO = TaskDTO.builder()
                .id(task.getId())
                .totalTime(task.getTotalTime())
                .comment(task.getComment())
                .project(projectDTO)
                .jobType(jobTypeDTO)
                .build();
        return taskDTO;
    }

    @Override
    public int countByWorkTimeAndDate(Long workTimeId, LocalDate workDate) {
        return taskRepository.countByWorkTimeAndDate(workTimeId,workDate);
    }

}


/*    //OLD
    //get all task
    @Override
    public List<TaskDTO> getAllTask() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for(Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTotalTime(task.getTotalTime());
            taskDTO.setComment(task.getComment());

            // Map workTime
            if (task.getWorkTime() != null) {
                WorkTimeDTO workTimeDTO = new WorkTimeDTO();
                workTimeDTO.setId(task.getWorkTime().getId());
                workTimeDTO.setDate(task.getWorkTime().getDate());
                taskDTO.setWorkTime(workTimeDTO);
            }

            // Map project
            if (task.getProject() != null) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(task.getProject().getId());
                projectDTO.setName(task.getProject().getName());
                taskDTO.setProject(projectDTO);
            }

            // Map jobType
            if (task.getJobType() != null) {
                JobTypeDTO jobTypeDTO = new JobTypeDTO();
                jobTypeDTO.setId(task.getJobType().getId());
                jobTypeDTO.setName(task.getJobType().getName());
                taskDTO.setJobType(jobTypeDTO);
            }

            //add task to taskDTO
            taskDTOS.add(taskDTO);
        }

        return taskDTOS;
    }

    //find task by id
    public TaskDTO getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTotalTime(task.getTotalTime());
            taskDTO.setComment(task.getComment());

            // Map workTime
            if (task.getWorkTime() != null) {
                WorkTimeDTO workTimeDTO = new WorkTimeDTO();
                workTimeDTO.setId(task.getWorkTime().getId());
                workTimeDTO.setDate(task.getWorkTime().getDate());
                taskDTO.setWorkTime(workTimeDTO);
            }

            // Map Project
            if (task.getProject() != null) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(task.getProject().getId());
                projectDTO.setName(task.getProject().getName());
                taskDTO.setProject(projectDTO);
            }

            // Map JobType
            if (task.getJobType() != null) {
                JobTypeDTO jobTypeDTO = new JobTypeDTO();
                jobTypeDTO.setId(task.getJobType().getId());
                jobTypeDTO.setName(task.getJobType().getName());
                taskDTO.setJobType(jobTypeDTO);
            }
            return taskDTO;
        }
        return null;
    }*/
