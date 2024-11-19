package com.example.demo.service.Task;

import com.example.demo.model.Task;
import com.example.demo.model.dto.TaskDTO;
import com.example.demo.repository.ITaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final ITaskRepository taskRepository;

    @Transactional
    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    @Override
    public Task save(Task model) {
        return taskRepository.save(model);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

    //get all workTime
    @Override
    public List<TaskDTO> getAllTask() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for(Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTotalTime(task.getTotalTime());
            taskDTO.setComment(task.getComment());

            //add worktime to worktimeDTO
            taskDTOS.add(taskDTO);
        }
        return taskDTOS;
    }

}
