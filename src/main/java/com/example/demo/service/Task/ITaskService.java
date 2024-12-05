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

    int countByWorkTimeAndDate(Long workTimeId, LocalDate workDate);
}
