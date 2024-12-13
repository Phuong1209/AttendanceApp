package com.example.demo.service.WorkTime;

import com.example.demo.dto.JobTypeDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.*;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IWorkTimeRepository;
import com.example.demo.utils.WorkTimeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkTimeService implements IWorkTimeService {

    private final IWorkTimeRepository workTimeRepository;
    private final WorkTimeMapper workTimeMapper;
    private final IUserRepository userRepository;

    // Get all workTime
    @Override
    public List<WorkTimeDTO> getAllWorkTime() {
        List<WorkTime> workTimes = workTimeRepository.findAll();
        return workTimes.stream()
                .map((workTime) -> mapToWorkTimeDTO(workTime))
                .collect(Collectors.toList());
    }

    // Mapper
    public WorkTimeDTO mapToWorkTimeDTO(WorkTime workTime) {
        UserDTO userDTO = UserDTO.builder()
                .id(workTime.getUser().getId())
                .fullName(workTime.getUser().getFullName())
                .build();

        // Map workTime to workTimeDTO
        WorkTimeDTO workTimeDTO = WorkTimeDTO.builder()
                .id(workTime.getId())
                .date(workTime.getDate())
                .checkinTime(workTime.getCheckinTime())
                .checkoutTime(workTime.getCheckoutTime())
                .breakTime(workTime.getBreakTime())
                .workTime(workTime.getWorkTime())
                .overTime(workTime.getOverTime())
                .user(userDTO)
                .build();

        return workTimeDTO;
    }

    //Validate date
    @Override
    public boolean existsByUserAndDate(Long userId, LocalDate date) {
        return workTimeRepository.existsByUser_IdAndDate(userId, date);
    }

    //Calculate
    public Map<String, Double> calculateWorkTime(LocalTime checkin, LocalTime checkout, Double breakTime) {
        long workedMinutes = java.time.Duration.between(checkin, checkout).toMinutes();
        double totalWorkTime = (workedMinutes / 60.0) - (breakTime != null ? breakTime : 0);

        if (totalWorkTime <= 0) {
            throw new IllegalArgumentException("作業時間に誤りがあります。");
        }

        double workTime = Math.min(totalWorkTime, 8);
        double overTime = Math.max(totalWorkTime - 8, 0);

        Map<String, Double> result = new HashMap<>();
        result.put("workTime", workTime);
        result.put("overTime", overTime);
        return result;
    }

    //Save
    public WorkTime saveWorkTime(WorkTime workTime) {
        return workTimeRepository.save(workTime);
    }

    // Find by Id
    @Override
    public WorkTimeDTO findById(long workTimeId) {
        WorkTime workTime = workTimeRepository.findById(workTimeId).get();
        return mapToWorkTimeDTO(workTime);
    }

    // Update
    @Override
    public void updateWorkTime(WorkTimeDTO workTimeDto) {
        WorkTime workTime = mapToWorkTime(workTimeDto);
        workTimeRepository.save(workTime);
    }

    // Map (to edit)
    private WorkTime mapToWorkTime(WorkTimeDTO workTimeDto) {
        // userDTO --> user
        User user = User.builder()
                .id(workTimeDto.getUser().getId())
                .fullName(workTimeDto.getUser().getFullName())
                .build();

        WorkTime workTime = WorkTime.builder()
                .id(workTimeDto.getId())
                .date(workTimeDto.getDate())
                .checkinTime(workTimeDto.getCheckinTime())
                .checkoutTime(workTimeDto.getCheckoutTime())
                .breakTime(workTimeDto.getBreakTime())
                .workTime(workTimeDto.getWorkTime())
                .overTime(workTimeDto.getOverTime())
                .user(user)
                .build();
        return workTime;
    }

    // Delete
    @Override
    public void delete(long workTimeId) {
        workTimeRepository.deleteById(workTimeId);
    }

    // Get list task
    public Set<Task> findTasksByWorkTime(Long workTimeId) {
        return workTimeRepository.findTasksByWorkTime(workTimeId);
    }

    // Get Weekday
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

// Get Calendar
    /*@Transactional
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
            dto.setHoliday(false);
            dto.setFuture(date.isAfter(LocalDate.now()));
            allDays.add(dto);
        }

        return allDays;
    }*/