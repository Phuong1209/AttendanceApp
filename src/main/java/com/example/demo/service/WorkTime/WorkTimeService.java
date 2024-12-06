//1206 added
package com.example.demo.service.WorkTime;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.Task;
import com.example.demo.model.WorkTime;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.repository.IWorkTimeRepository;
import com.example.demo.utils.WorkTimeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkTimeService implements IWorkTimeService {

    private final IWorkTimeRepository workTimeRepository;
    private final ITaskRepository taskRepository;
    private final WorkTimeMapper workTimeMapper;

    @Transactional
    @Override
    public List<WorkTimeDTO> getAllWorkTime() {
        List<WorkTime> workTimes = workTimeRepository.findAll();
        List<WorkTimeDTO> workTimeDTOS = new ArrayList<>();

        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(2024, 12, 25), // Christmas Day
                LocalDate.of(2024, 1, 1)   // New Year's Day
        );

        for (WorkTime workTime : workTimes) {
            WorkTimeDTO workTimeDTO = new WorkTimeDTO();
            workTimeDTO.setId(workTime.getId());
            workTimeDTO.setDate(workTime.getDate());
            workTimeDTO.setCheckinTime(workTime.getCheckinTime());
            workTimeDTO.setCheckoutTime(workTime.getCheckoutTime());
            workTimeDTO.setBreakTime(workTime.getBreakTime());
            workTimeDTO.setWorkTime(workTime.getWorkTime());
            workTimeDTO.setOverTime(workTime.getOverTime());

            String weekday = getWeekdayString(workTime.getDate());
            workTimeDTO.setWeekday(weekday);
            workTimeDTO.setWeekend(workTime.getDate().getDayOfWeek().getValue() >= 6);
            workTimeDTO.setHoliday(holidays.contains(workTime.getDate()));
            workTimeDTO.setFuture(workTime.getDate().isAfter(LocalDate.now()));

            log.info("WorkTimeDTO for date {}: isHoliday={}, isWeekend={}, isFuture={}",
                    workTime.getDate(), workTimeDTO.isHoliday(), workTimeDTO.isWeekend(), workTimeDTO.isFuture());

            workTimeDTOS.add(workTimeDTO);
        }
        return workTimeDTOS;
    }

    @Transactional
    @Override
    public WorkTimeDTO getWorkTimeById(Long id) {
        return workTimeRepository.findById(id)
                .map(workTime -> {
                    List<Task> tasks = taskRepository.findByWorkTime(workTime);
                    return workTimeMapper.toDto(workTime, tasks);
                })
                .orElse(null);
    }

    @Override
    public Iterable<WorkTime> findAll() {
        return workTimeRepository.findAll();
    }

    @Override
    public Optional<WorkTime> findById(Long id) {
        return workTimeRepository.findById(id);
    }

    @Override
    public WorkTime save(WorkTime model) {
        return workTimeRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        workTimeRepository.deleteById(id);
    }

    @Override
    public Iterable<WorkTime> findAllByUserId(Long userId) {
        return workTimeRepository.findAllByUserId(userId);
    }

    @Transactional
    @Override
    public List<WorkTimeDTO> getWorkTimeForUserAndMonth(Long userId, int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        List<WorkTime> workTimes = workTimeRepository.findByUserIdAndDateBetween(userId, startOfMonth, endOfMonth);

        Map<LocalDate, WorkTimeDTO> workTimeMap = workTimes.stream()
                .map(workTime -> workTimeMapper.toDto(workTime, new ArrayList<>(workTime.getTasks())))
                .collect(Collectors.toMap(WorkTimeDTO::getDate, dto -> dto));


        List<WorkTimeDTO> allDays = new ArrayList<>();
        for (LocalDate date = startOfMonth; !date.isAfter(endOfMonth); date = date.plusDays(1)) {
            WorkTimeDTO dto = workTimeMap.getOrDefault(date, new WorkTimeDTO());
            dto.setDate(date);
            dto.setWeekday(getWeekdayString(date));
            dto.setWeekend(date.getDayOfWeek().getValue() >= 6);
            dto.setHoliday(false); // Add custom holiday logic if needed
            dto.setFuture(date.isAfter(LocalDate.now()));
            allDays.add(dto);
        }

        return allDays;
    }

    @Override
    public List<Task> getTaskByWorkTime(Long id) {
        if (id != null) {
            Optional<WorkTime> optionalWorkTime = workTimeRepository.findById(id);
            if (optionalWorkTime.isPresent()) {
                WorkTime foundWorkTime = optionalWorkTime.get();
                List<Task> tasks = taskRepository.findByWorkTime(foundWorkTime);
                log.info("Task of workTime {}: {}", foundWorkTime.getId(), tasks);
                return tasks;
            }
        }
        return Collections.emptyList();
    }

    private String getWeekdayString(LocalDate date) {
        switch (date.getDayOfWeek()) {
            case MONDAY: return "月";
            case TUESDAY: return "火";
            case WEDNESDAY: return "水";
            case THURSDAY: return "木";
            case FRIDAY: return "金";
            case SATURDAY: return "土";
            case SUNDAY: return "日";
            default: return "";
        }
    }
}





/*
package com.example.demo.service.WorkTime;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
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

    @Override
    public void delete(User user) {

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

    @Override
    public Iterable<WorkTime> findAllByUserId(Long id) {
        return workTimeRepository.findAllById();
    }

}
*/
