package com.example.demo.service.Task;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.ITaskRepository;
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

    //save
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

        //workTime --> workTimeDto
        WorkTimeDTO workTimeDTO = WorkTimeDTO.builder()
                .id(task.getWorkTime().getId())
                .date(task.getWorkTime().getDate())
                .build();

        TaskDTO taskDTO = TaskDTO.builder()
                .id(task.getId())
                .totalTime(task.getTotalTime())
                .comment(task.getComment())
                .project(projectDTO)
                .jobType(jobTypeDTO)
                .workTime(workTimeDTO)
                .build();
        return taskDTO;
    }

    @Override
    public TaskDTO findById(long taskId) {
        Task task = taskRepository.findById(taskId).get();
        return mapToTaskDTO(task);
    }

    @Override
    public void updateTask(TaskDTO taskDto) {
        Task task = mapToTask(taskDto);
        taskRepository.save(task);
    }

    //Map to edit
    private Task mapToTask(TaskDTO taskDto){
        //jobtypeDto --> jobtype
        JobType jobType = JobType.builder()
                .id(taskDto.getJobType().getId())
                .name(taskDto.getJobType().getName())
                .build();

        //projectDto --> project
        Project project = Project.builder()
                .id(taskDto.getProject().getId())
                .name(taskDto.getProject().getName())
                .build();

        //workTimeDto --> workTime
        WorkTime workTime = WorkTime.builder()
                .id(taskDto.getWorkTime().getId())
                .date(taskDto.getWorkTime().getDate())
                .build();

        Task task = Task.builder()
                .id(taskDto.getId())
                .totalTime(taskDto.getTotalTime())
                .comment(taskDto.getComment())
                .project(project)
                .jobType(jobType)
                .workTime(workTime)
                .build();
        return task;
    }

    //Delete
    @Override
    public void delete(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public int countByWorkTimeAndDate(Long workTimeId, LocalDate workDate) {
        return taskRepository.countByWorkTimeAndDate(workTimeId,workDate);
    }
}
