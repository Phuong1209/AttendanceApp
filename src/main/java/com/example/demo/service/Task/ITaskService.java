package com.example.demo.service.Task;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.model.Task;
import com.example.demo.dto.TaskDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITaskService {
    //CRUD
    List<TaskDTO> getAllTask();
    Task saveTask(Task task);
    TaskDTO findById(long taskId);
    void updateTask(TaskDTO taskDto);
    void delete(long taskId);

    //set max task
    int countByWorkTimeAndDate(Long workTimeId, LocalDate workDate);

}
