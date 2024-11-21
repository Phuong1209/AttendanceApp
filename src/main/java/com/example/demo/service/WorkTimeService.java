/*
//Code P
package com.example.demo.service.WorkTime;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.*;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class WorkTimeService implements IWorkTimeService {

    private final IWorkTimeRepository workTimeRepository;
    private final ITaskRepository taskRepository;

    @Transactional
    @Override
    public Iterable<WorkTime> findAll() {
        return workTimeRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<WorkTime> findById(Long id) {
        return workTimeRepository.findById(id);
    }

    @Transactional
    @Override
    public WorkTime save(WorkTime model) {
        return workTimeRepository.save(model);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        workTimeRepository.deleteById(id);
    }

    //list task
    public List<Task> getTaskByWorkTime(Long workTimeId) {
        if(workTimeId != null){
            Optional<WorkTime>optionalWorkTime = workTimeRepository.findById(workTimeId);
            if(optionalWorkTime.isPresent()) {
                WorkTime foundWorkTime= optionalWorkTime.get();
                List<Task> tasks = taskRepository.findByWorkTime(foundWorkTime);
                log.info("Task of workTime {}:{}",foundWorkTime.getId(), tasks);
                return tasks;
            }
        }
        return Collections.emptyList();
    }

    //get all workTime
    @Override
    public List<WorkTimeDTO> getAllWorkTime() {
        List<WorkTime> workTimes = workTimeRepository.findAll();
        List<WorkTimeDTO> workTimeDTOS = new ArrayList<>();
        for (WorkTime workTime : workTimes) {
            WorkTimeDTO workTimeDTO = new WorkTimeDTO();
            workTimeDTO.setId(workTime.getId());
            workTimeDTO.setDate(workTime.getDate());
            workTimeDTO.setCheckinTime(workTime.getCheckinTime());
            workTimeDTO.setCheckoutTime(workTime.getCheckoutTime());
            workTimeDTO.setBreakTime(workTime.getBreakTime());
            workTimeDTO.setWorkTime(workTime.getWorkTime());
            workTimeDTO.setOverTime(workTime.getOverTime());

            // Map tasks to TaskDTO
            List<Task> tasks = taskRepository.findByWorkTime(workTime);
            Set<TaskDTO> taskDTOS = tasks.stream().map(task -> {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTotalTime(task.getTotalTime());
                taskDTO.setComment(task.getComment());
                return taskDTO;
            }).collect(Collectors.toSet());
            workTimeDTO.setTasks(taskDTOS);

            // Map user
            if (workTime.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(workTime.getUser().getId());
                userDTO.setUserName(workTime.getUser().getUserName());
                workTimeDTO.setUser(userDTO);
            }

            // Add to list
            workTimeDTOS.add(workTimeDTO);
        }
        return workTimeDTOS;
    }

    //find workTime by id
    public WorkTimeDTO getWorkTimeById(Long id) {
        Optional<WorkTime> workTimeOptional = workTimeRepository.findById(id);
        if (workTimeOptional.isPresent()) {
            WorkTime workTime = workTimeOptional.get();
            WorkTimeDTO workTimeDTO = new WorkTimeDTO();
            workTimeDTO.setId(workTime.getId());
            workTimeDTO.setDate(workTime.getDate());
            workTimeDTO.setCheckinTime(workTime.getCheckinTime());
            workTimeDTO.setCheckoutTime(workTime.getCheckoutTime());
            workTimeDTO.setBreakTime(workTime.getBreakTime());
            workTimeDTO.setWorkTime(workTime.getWorkTime());
            workTimeDTO.setOverTime(workTime.getOverTime());

            // Map tasks
            List<Task> tasks = taskRepository.findByWorkTime(workTime);
            Set<TaskDTO> taskDTOS = tasks.stream().map(task -> {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setTotalTime(task.getTotalTime());
                taskDTO.setComment(task.getComment());
                return taskDTO;
            }).collect(Collectors.toSet());
            workTimeDTO.setTasks(taskDTOS);

            // Map user
            if (workTime.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(workTime.getUser().getId());
                userDTO.setUserName(workTime.getUser().getUserName());
                workTimeDTO.setUser(userDTO);
            }

            return workTimeDTO;
        }
        return null;
    }



}


//Code TA
package com.example.demo.service;

import com.example.demo.model.WorkingTime;
import com.example.demo.repository.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkTimeService implements IWorkTimeService {
    @Autowired
    WorkTimeRepository workTimeRepository;

    @Override
    public WorkingTime checkin(Long userId, LocalDateTime checkinTime) {
        LocalDate checkinDate = checkinTime.toLocalDate();

        Optional<WorkingTime> openWorkTime = workTimeRepository.findUnclosedByUserIdAndDate(userId, checkinDate);

        if (openWorkTime.isPresent()) {
            // Update check-in time for the open entry
            WorkingTime workingTime = openWorkTime.get();
            workingTime.setCheckin_time(checkinTime);
            return workTimeRepository.save(workingTime);
        } else {
            // Create a new record if no open entry is found
            WorkingTime workingTime = new WorkingTime();
            workingTime.setUser_id(userId);
            workingTime.setCheckin_time(checkinTime);
            workingTime.setDate(checkinDate);
            return workTimeRepository.save(workingTime);
        }
    }

    @Override
    public WorkingTime checkout(Long userId, LocalDateTime checkoutTime) {
        LocalDate checkoutDate = checkoutTime.toLocalDate();

        // Retrieve the latest open check-in record for the specific date
        WorkingTime workingTime = workTimeRepository.findLatestUnclosedByUserIdAndDate(userId, checkoutDate)
                .orElseThrow(() -> new IllegalStateException("No open check-in record found for user ID " + userId + " on " + checkoutDate));

        workingTime.setUser_id(userId);

        // Check if checkoutTime is on the same day as checkinTime
        if (!workingTime.getCheckin_time().toLocalDate().equals(checkoutTime.toLocalDate())) {
            throw new IllegalArgumentException("Check-out time must be on the same day as check-in time.");
        }

        workingTime.setCheckout_time(checkoutTime);
        calculateWorkAndOvertime(workingTime);
        return workTimeRepository.save(workingTime);


        }

    @Override
    public WorkingTime updateBreaktime(Long userId, Float breaktimeHours) {
        Optional<WorkingTime> optionalWorkingTime = workTimeRepository.findLatestUnclosedByUserIdOrderByCheckin_timeDesc(userId);

        if (optionalWorkingTime.isPresent()) {
            WorkingTime workingTime = optionalWorkingTime.get();
            workingTime.setBreaktime(breaktimeHours);
            return workTimeRepository.save(workingTime);
        } else {
            throw new IllegalStateException("No check-in record found for user ID " + userId);
        }

    }

    public boolean hasClosedRecordForDate(Long userId, LocalDate date) {
        Optional<WorkingTime> existingWorkTime = workTimeRepository.findByUserIdAndDate(userId, date);
        // If a record exists and has a checkout time, return true
        return existingWorkTime.isPresent() && existingWorkTime.get().getCheckout_time() != null;
    }

    public boolean hasRecordForDate(Long userId, LocalDate date) {
        // Check if any record exists for this user and date
        return workTimeRepository.findByUserIdAndDate(userId, date).isPresent();
    }

    @Override
    public List<WorkingTime> getWorkTimesByUser(Long userId) {
        return workTimeRepository.findByUserId(userId);
    }

    @Override
    public void calculateWorkAndOvertime(WorkingTime workingTime) {
        Duration totalWorkDuration = Duration.between(workingTime.getCheckin_time(), workingTime.getCheckout_time());

        // Subtract break time in hours from the total duration
        if (workingTime.getBreaktime() != null) {
            totalWorkDuration = totalWorkDuration.minus(Duration.ofMinutes((long)(workingTime.getBreaktime() * 60)));
        }

        workingTime.setWorktime(LocalDateTime.of(0, 1, 1, totalWorkDuration.toHoursPart(), totalWorkDuration.toMinutesPart()));

        // Calculate overtime if work exceeds 8 hours
        if (totalWorkDuration.toHours() > 8) {
            Duration overtimeDuration = totalWorkDuration.minusHours(8);
            workingTime.setOvertime(LocalDateTime.of(0, 1, 1, overtimeDuration.toHoursPart(), overtimeDuration.toMinutesPart()));
        } else {
            workingTime.setOvertime(LocalDateTime.of(0, 1, 1, 0, 0));
        }
    }

    @Override
    public List<WorkingTime> getAllWorkTimes() {
        return workTimeRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        workTimeRepository.deleteById(id);
    }

    @Override
    public Optional<WorkingTime> findByUserIdAndDate(Long userId, LocalDate date) {
        return workTimeRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public WorkingTime getById(Long id) {
        return workTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No work time record found for ID " + id));
    }

    @Override
    public void save(WorkingTime workingTime) {
        Optional<WorkingTime> existingRecord = workTimeRepository.findByUserIdAndDate(workingTime.getUser_id(), workingTime.getDate());

        // If record exists for the same user and date, throw an exception
        if (existingRecord.isPresent()) {
            throw new IllegalArgumentException("A record already exists for this user on the selected date.");
        }

        workTimeRepository.save(workingTime);
    }

    @Override
    public void updateWorkingTime(Long id, Long userId, LocalDateTime checkinTime, LocalDateTime checkoutTime, Float breaktime) {
        WorkingTime workingTime = workTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No working time record found with ID " + id));

        // Extract the date from the new check-in time
        LocalDate checkinDate = checkinTime.toLocalDate();
        LocalDate checkoutDate = checkoutTime.toLocalDate();

        // Check for any other record with the same user ID and date
        Optional<WorkingTime> conflictingRecord = workTimeRepository.findByUserIdAndDate(userId, checkinDate);

        // If a conflicting record exists and it's not the one being updated, throw an exception
        if (conflictingRecord.isPresent() && !conflictingRecord.get().getId().equals(id)) {
            throw new IllegalArgumentException("A record for this user already exists on this date.");
        }
        // Ensure that the check-out time is on the same day as the check-in time
        if (!checkinDate.equals(checkoutDate)) {
            throw new IllegalArgumentException("Check-out time must be on the same day as the check-in time.");
        }

        workingTime.setCheckin_time(checkinTime);
        workingTime.setCheckout_time(checkoutTime);
        workingTime.setBreaktime(breaktime);

        calculateWorkAndOvertime(workingTime);  // Recalculate work and overtime
        workTimeRepository.save(workingTime);
    }
}
*/
