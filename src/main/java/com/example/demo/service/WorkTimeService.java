package com.example.demo.service;

import com.example.demo.model.WorkingTime;
import com.example.demo.repository.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkTimeService implements IWorkTimeService {
    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Override
    public WorkingTime checkin(Long userId, LocalDateTime checkinTime) {
        WorkingTime workingTime = new WorkingTime();
        workingTime.setUser_id(userId);
        workingTime.setCheckin_time(checkinTime);
        workingTime.setDate(LocalDate.now()); // Assume check-in is done for the current day
        return workTimeRepository.save(workingTime);
    }

    @Override
    public WorkingTime checkout(Long userId, LocalDateTime checkoutTime) {
        WorkingTime workingTime = workTimeRepository.findLatestByUserId(userId);
        workingTime.setCheckout_time(checkoutTime);
        calculateWorkAndOvertime(workingTime);
        return workTimeRepository.save(workingTime);
    }

    @Override
    public WorkingTime updateBreaktime(Long userId, LocalDateTime breaktime) {
        WorkingTime workingTime = workTimeRepository.findLatestByUserId(userId);
        workingTime.setBreaktime(breaktime);
        return workTimeRepository.save(workingTime);
    }


    @Override
    public List<WorkingTime> getWorkTimesByUser(Long userId) {
        return workTimeRepository.findByUserId(userId);
    }

    @Override
    public void calculateWorkAndOvertime(WorkingTime workingTime) {
        // Calculate total work time excluding break time
        Duration totalWorkDuration = Duration.between(workingTime.getCheckin_time(), workingTime.getCheckout_time())
                .minus(Duration.between(LocalDateTime.MIN, workingTime.getBreaktime()));

        workingTime.setWorktime(LocalDateTime.of(0, 1, 1, totalWorkDuration.toHoursPart(), totalWorkDuration.toMinutesPart()));

        // Calculate overtime if work exceeds 8 hours
        if (totalWorkDuration.toHours() > 8) {
            Duration overtimeDuration = totalWorkDuration.minusHours(8);
            workingTime.setOvertime(LocalDateTime.of(0, 1, 1, overtimeDuration.toHoursPart(), overtimeDuration.toMinutesPart()));
        } else {
            workingTime.setOvertime(LocalDateTime.of(0, 1, 1, 0, 0));
        }
    }
}
