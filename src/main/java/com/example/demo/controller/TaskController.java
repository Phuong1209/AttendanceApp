package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.Task.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/task")

public class TaskController {
    @Autowired
    private ITaskService taskService;

    //show list
    public ResponseEntity<Iterable<Task>> getAllTask() {
        List<Task> taskList = (List<Task>) taskService.findAll();
        if (taskList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<Task> findTasksById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);
        return taskOptional.map(Task -> new ResponseEntity<>(Task, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //create
    @PostMapping("")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        taskService.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(@PathVariable Long id, @RequestBody Task task) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        task.setId(id);
        taskService.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
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
