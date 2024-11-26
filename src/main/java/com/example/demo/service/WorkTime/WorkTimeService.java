package com.example.demo.service.WorkTime;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public void delete(User user) {

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
        for (WorkTime workTime : workTimes) {
            WorkTimeDTO workTimeDTO = new WorkTimeDTO();
            workTimeDTO.setId(workTime.getId());
            workTimeDTO.setDate(workTime.getDate());
            workTimeDTO.setCheckinTime(LocalTime.from(workTime.getCheckinTime()));
            workTimeDTO.setCheckoutTime(LocalTime.from(workTime.getCheckoutTime()));
            workTimeDTO.setBreakTime(workTime.getBreakTime());
            workTimeDTO.setWorkTime(workTime.getWorkTime());
            workTimeDTO.setOverTime(workTime.getOverTime());

            // Map tasks to TaskDTO
            List<Task> tasks = taskRepository.findByWorkTime(workTime);
            Set<TaskDTO> taskDTOS = tasks.stream().map(task -> {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTotalTime(task.getTotalTime());
                taskDTO.setComment(task.getComment());
                return taskDTO;
            }).collect(Collectors.toSet());
            workTimeDTO.setTasks(taskDTOS);

            // Map user
            if (workTime.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(workTime.getUser().getId());
                userDTO.setUserName(workTime.getUser().getUserName());
                workTimeDTO.setUser(userDTO);
            }

            // Add to list
            workTimeDTOS.add(workTimeDTO);
        }
        return workTimeDTOS;
    }

    //find workTime by id
    public WorkTimeDTO getWorkTimeById(Long id) {
        Optional<WorkTime> workTimeOptional = workTimeRepository.findById(id);
        if (workTimeOptional.isPresent()) {
            WorkTime workTime = workTimeOptional.get();
            WorkTimeDTO workTimeDTO = new WorkTimeDTO();
            workTimeDTO.setId(workTime.getId());
            workTimeDTO.setDate(workTime.getDate());
            workTimeDTO.setCheckinTime(LocalTime.from(workTime.getCheckinTime()));
            workTimeDTO.setCheckoutTime(LocalTime.from(workTime.getCheckoutTime()));
            workTimeDTO.setBreakTime(workTime.getBreakTime());
            workTimeDTO.setWorkTime(workTime.getWorkTime());
            workTimeDTO.setOverTime(workTime.getOverTime());

            // Map tasks
            List<Task> tasks = taskRepository.findByWorkTime(workTime);
            Set<TaskDTO> taskDTOS = tasks.stream().map(task -> {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTotalTime(task.getTotalTime());
                taskDTO.setComment(task.getComment());
                return taskDTO;
            }).collect(Collectors.toSet());
            workTimeDTO.setTasks(taskDTOS);

            // Map user
            if (workTime.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(workTime.getUser().getId());
                userDTO.setUserName(workTime.getUser().getUserName());
                workTimeDTO.setUser(userDTO);
            }

            return workTimeDTO;
        }
        return null;
    }

}
