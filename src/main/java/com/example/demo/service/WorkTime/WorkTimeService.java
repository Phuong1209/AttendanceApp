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

    //get all workTime
    @Override
    public List<WorkTimeDTO> getAllWorkTime() {
        List<WorkTime> workTimes = workTimeRepository.findAll();
        return workTimes.stream().map((workTime) -> mapToWorkTimeDTO(workTime)).collect(Collectors.toList());
    }

    //mapper
    public WorkTimeDTO mapToWorkTimeDTO(WorkTime workTime) {
        //map workTime to workTimeDto
        WorkTimeDTO workTimeDTO = WorkTimeDTO.builder()
                .id(workTime.getId())
                .date(workTime.getDate())
                .checkinTime(workTime.getCheckinTime())
                .checkoutTime(workTime.getCheckoutTime())
                .breakTime(workTime.getBreakTime())
                .workTime(workTime.getWorkTime())
                .overTime(workTime.getOverTime())
                .build();
        return workTimeDTO;
    }

    //Save WorkTime
    public WorkTime saveWorkTime(WorkTime workTime){
        return workTimeRepository.save(workTime);
    }

    //Find by Id
    @Override
    public WorkTimeDTO findById(long workTimeId) {
        WorkTime workTime = workTimeRepository.findById(workTimeId).get();
        return mapToWorkTimeDTO(workTime);
    }

    //Update
    @Override
    public void updateWorkTime(WorkTimeDTO workTimeDto) {
        WorkTime workTime = mapToWorkTime(workTimeDto);
        workTimeRepository.save(workTime);
    }

    //Map (to edit)
    private WorkTime mapToWorkTime(WorkTimeDTO workTimeDto){
        WorkTime workTime = WorkTime.builder()
                .id(workTimeDto.getId())
                .date(workTimeDto.getDate())
                .checkinTime(workTimeDto.getCheckinTime())
                .checkoutTime(workTimeDto.getCheckoutTime())
                .breakTime(workTimeDto.getBreakTime())
                .workTime(workTimeDto.getWorkTime())
                .overTime(workTimeDto.getOverTime())
                .build();
        return workTime;
    }

    //Delete
    @Override
    public void delete(long workTimeId) {
        workTimeRepository.deleteById(workTimeId);
    }

}
