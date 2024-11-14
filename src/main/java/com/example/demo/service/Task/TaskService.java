package com.example.demo.service.Task;

import com.example.demo.model.Department;
import com.example.demo.model.Task;
import com.example.demo.model.WorkTime;
//import com.example.demo.model.dto.TaskDto;
//import com.example.demo.model.dto.WorkTimeDto;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }
    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task model) {
        return taskRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

/*    @Override
    public List<TaskDto> getAllTask() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> taskDtos = new ArrayList<>();
        for(Task task : tasks){
            TaskDto taskDto = new TaskDto();
            taskDto.setId(task.getId());
            //add DepartmentDto to Dtos
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }*/

}
