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

        //        WorkingTime workingTime = workTimeRepository.findLatestByUserId(userId);
//        if (workingTime.getCheckin_time() == null) {
//            throw new IllegalStateException("No check-in record found for user ID " + userId + " on " + checkoutDate);
//        }
//        WorkingTime workingTime = workingTime.;
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


}
