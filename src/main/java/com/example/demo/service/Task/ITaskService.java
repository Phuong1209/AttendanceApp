package com.example.demo.service.Task;

import com.example.demo.model.Task;
import com.example.demo.dto.TaskDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITaskService extends IGeneralService<Task> {
    List<TaskDTO> getAllTask();

    TaskDTO getTaskById(Long taskId);

    int countByWorkTimeAndDate(Long workTimeId, LocalDate workDate);
}
