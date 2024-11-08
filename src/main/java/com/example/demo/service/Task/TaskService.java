package com.example.demo.service.Task;

import com.example.demo.model.Department;
import com.example.demo.model.Task;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
