package com.example.demo.service.Task;

import com.example.demo.model.Task;
import com.example.demo.model.dto.TaskDTO;
import com.example.demo.model.dto.WorkTimeDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskService extends IGeneralService<Task> {
    List<TaskDTO> getAllTask();

}
