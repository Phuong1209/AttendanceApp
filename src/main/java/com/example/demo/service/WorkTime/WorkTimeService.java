package com.example.demo.service.WorkTime;

import com.example.demo.model.*;
import com.example.demo.model.dto.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

            //add user to user DTO
            workTimeDTOS.add(workTimeDTO);
        }
        return workTimeDTOS;
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

}
