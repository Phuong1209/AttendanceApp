package com.example.demo.service.WorkTime;

import com.example.demo.model.*;
import com.example.demo.model.dto.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.repository.IWorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkTimeService implements IWorkTimeService {
    @Autowired
    private IWorkTimeRepository workTimeRepository;
    private ITaskRepository taskRepository;

    @Override
    public Iterable<WorkTime> findAll() {
        return workTimeRepository.findAll();
    }
    @Override
    public Optional<WorkTime> findById(Long id) {
        return workTimeRepository.findById(id);
    }

    @Override
    public WorkTime save(WorkTime model) {
        return workTimeRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        workTimeRepository.deleteById(id);
    }

    /*@Override
    public List<WorkTimeDto> getAllWorkTime() {
        List<WorkTime> workTimes = workTimeRepository.findAll();
        List<WorkTimeDto> workTimeDtos = new ArrayList<>();
        for(WorkTime workTime : workTimes){
            WorkTimeDto workTimeDto = new WorkTimeDto();
            workTimeDto.setId(workTime.getId());

            //get workTime's task list
            List<Task> tasks = taskRepository.findByWorkTime(workTime);
            Set<TaskDto> taskDtos = new HashSet<>();
            for (Task task : tasks){
                TaskDto taskDto = new TaskDto();
                taskDto.setId(task.getId());
                taskDto.setTotalTime(task.getTotalTime());
                taskDto.setJobType(task.getJobType());
                taskDtos.add(taskDto);
            }
            //set jobTypeDtos to departmentDto
            workTimeDto.setTasks(taskDtos);

            //add DepartmentDto to Dtos
            workTimeDtos.add(workTimeDto);
        }
        return workTimeDtos;
    }
*/
}
