package com.example.demo.utils;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.Task;
import com.example.demo.model.WorkTime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkTimeMapper {

    public WorkTimeDTO toDto(WorkTime workTime, List<Task> tasks) {
        WorkTimeDTO workTimeDTO = new WorkTimeDTO();
        workTimeDTO.setId(workTime.getId());
        workTimeDTO.setDate(workTime.getDate());
        workTimeDTO.setCheckinTime(workTime.getCheckinTime());
        workTimeDTO.setCheckoutTime(workTime.getCheckoutTime());
        workTimeDTO.setBreakTime(workTime.getBreakTime());
        workTimeDTO.setWorkTime(workTime.getWorkTime());
        workTimeDTO.setOverTime(workTime.getOverTime());

        // Map tasks
        workTimeDTO.setTasks(tasks.stream().map(task -> {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTotalTime(task.getTotalTime());
            taskDTO.setComment(task.getComment());
            return taskDTO;
        }).collect(Collectors.toSet()));

        // Map user
        if (workTime.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(workTime.getUser().getId());
            userDTO.setUserName(workTime.getUser().getUserName());
            workTimeDTO.setUser(userDTO);
        }

        return workTimeDTO;
    }
}
