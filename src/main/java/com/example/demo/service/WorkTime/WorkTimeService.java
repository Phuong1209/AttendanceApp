package com.example.demo.service.WorkTime;

import com.example.demo.model.*;
import com.example.demo.model.dto.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor

public class WorkTimeService implements IWorkTimeService {

    private final IWorkTimeRepository workTimeRepository;
    private final ITaskRepository taskRepository;

    @Transactional
    @Override
    public Iterable<WorkTime> findAll() {
        return workTimeRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<WorkTime> findById(Long id) {
        return workTimeRepository.findById(id);
    }

    @Transactional
    @Override
    public WorkTime save(WorkTime model) {
        return workTimeRepository.save(model);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        workTimeRepository.deleteById(id);
    }

    //list task
    public List<Task> getTaskByWorkTime(Long workTimeId) {
        if(workTimeId != null){
            Optional<WorkTime>optionalWorkTime = workTimeRepository.findById(workTimeId);
            if(optionalWorkTime.isPresent()) {
                WorkTime foundWorkTime= optionalWorkTime.get();
                List<Task> tasks = taskRepository.findByWorkTime(foundWorkTime);
                log.info("Task of workTime {}:{}",foundWorkTime.getId(), tasks);
                return tasks;
            }
        }
        return Collections.emptyList();
    }

    //get all workTime
    @Override
    public List<WorkTimeDTO> getAllWorkTime() {
        List<WorkTime> workTimes = workTimeRepository.findAll();
        List<WorkTimeDTO> workTimeDTOS = new ArrayList<>();
        for(WorkTime workTime : workTimes) {
            WorkTimeDTO workTimeDTO = new WorkTimeDTO();
            workTimeDTO.setId(workTime.getId());
            workTimeDTO.setDate(workTime.getDate());
            workTimeDTO.setCheckinTime(workTime.getCheckinTime());
            workTimeDTO.setCheckoutTime(workTime.getCheckoutTime());
            workTimeDTO.setBreakTime(workTime.getBreakTime());
            workTimeDTO.setWorkTime(workTime.getWorkTime());
            workTimeDTO.setOverTime(workTime.getOverTime());
            //workTimeDTO.setUser(workTime.getUser());

            //get task list
            List<Task> tasks = taskRepository.findByWorkTime(workTime);
            Set<TaskDTO> taskDTOS = new HashSet<>();
            for(Task task : tasks) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTotalTime(task.getTotalTime());
                taskDTO.setComment(task.getComment());
                taskDTOS.add(taskDTO);
            }
            workTimeDTO.setTasks(taskDTOS);

            //add worktime to worktimeDTO
            workTimeDTOS.add(workTimeDTO);
        }
        return workTimeDTOS;
    }

    //Edit
    @Transactional
    public WorkTimeDTO editWorkTime(Long workTimeId, LocalTime checkinTime, LocalTime checkoutTime, Float breakTime, Set<Long> newTaskIds) {
        // Find the workTime by ID
        Optional<WorkTime> optionalWorkTime = workTimeRepository.findById(workTimeId);
        if (!optionalWorkTime.isPresent()) {
            throw new NoSuchElementException("WorkTime not found with ID: " + workTimeId);
        }

        WorkTime workTime = optionalWorkTime.get();

        // Update project's attribute
        workTime.setCheckinTime(checkinTime);
        workTime.setCheckoutTime(checkoutTime);
        workTime.setBreakTime(breakTime);

        //caculate
        Duration duration = Duration.between(workTime.getCheckinTime(), workTime.getCheckoutTime());
        float workTimeHours = duration.toMinutes() / 60.0f - workTime.getBreakTime();

        // Set workTime and overTime based on the calculations
        workTime.setWorkTime(workTimeHours);
        workTime.setOverTime(workTimeHours > 8 ? workTimeHours - 8 : 0);

        // Update the workTime's tasks
        if (newTaskIds != null && !newTaskIds.isEmpty()) {
            // Fetch the new Task entities by their IDs
            List<Task> newTasks = taskRepository.findAllById(newTaskIds);
            if (newTasks.size() != newTaskIds.size()) {
                throw new IllegalArgumentException("One or more JobType IDs are invalid.");
            }
            workTime.setTasks(new HashSet<>(newTasks));
        }

        // Save the updated workTime
        WorkTime updatedWorkTime = workTimeRepository.save(workTime);

        // Map the updated department to DepartmentDTO
        WorkTimeDTO workTimeDTO = new WorkTimeDTO();
        workTimeDTO.setId(updatedWorkTime.getId());
        workTimeDTO.setCheckinTime(updatedWorkTime.getCheckinTime());
        workTimeDTO.setCheckoutTime(updatedWorkTime.getCheckoutTime());
        workTimeDTO.setBreakTime(updatedWorkTime.getBreakTime());
        workTimeDTO.setWorkTime(updatedWorkTime.getWorkTime());
        workTimeDTO.setOverTime(updatedWorkTime.getOverTime());

        // Map tasks to TaskDTO
        Set<TaskDTO> taskDTOS = new HashSet<>();
        for (Task task : updatedWorkTime.getTasks()) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTotalTime(task.getTotalTime());
            taskDTO.setComment(task.getComment());
            taskDTOS.add(taskDTO);
        }
        workTimeDTO.setTasks(taskDTOS);

        return workTimeDTO;
    }

}
