package com.example.demo.service.WorkTime;

import com.example.demo.dto.*;
import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class WorkTimeService implements IWorkTimeService {

    private final IWorkTimeRepository workTimeRepository;
    private final ITaskRepository taskRepository;

    //get all workTime
    @Override
    public List<WorkTimeDTO> getAllWorkTime() {
        List<WorkTime> workTimes = workTimeRepository.findAll();
        return workTimes.stream().map((workTime) -> mapToWorkTimeDTO(workTime)).collect(Collectors.toList());
    }

    //mapper
    public WorkTimeDTO mapToWorkTimeDTO(WorkTime workTime) {
        //map list tasks to taskDtos
        Set<TaskDTO> taskDTOs = workTime.getTasks().stream()
                .map((tasks) -> new TaskDTO(tasks.getId(), tasks.getComment(), tasks.getTotalTime()))
                .collect(Collectors.toSet());

        //map workTime to workTimeDto
        WorkTimeDTO workTimeDTO = WorkTimeDTO.builder()
                .id(workTime.getId())
                .date(workTime.getDate())
                .checkinTime(workTime.getCheckinTime())
                .checkoutTime(workTime.getCheckoutTime())
                .breakTime(workTime.getBreakTime())
                .workTime(workTime.getWorkTime())
                .overTime(workTime.getOverTime())
                .tasks(taskDTOs)
                .build();

        return workTimeDTO;
    }

    //Save WorkTime
    public WorkTime saveWorkTime(WorkTime workTime){
        return workTimeRepository.save(workTime);
    }

}
