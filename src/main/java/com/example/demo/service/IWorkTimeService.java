package com.example.demo.service;

import com.example.demo.model.WorkingTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IWorkTimeService {
    WorkingTime checkin(Long userId, LocalDateTime checkinTime);      // Check-in user
    WorkingTime checkout(Long userId, LocalDateTime checkoutTime);    // Check-out user
    WorkingTime updateBreaktime(Long userId, Float breaktimeHours); // Set/update breaktime
    List<WorkingTime> getWorkTimesByUser(Long userId);             // Retrieve user's work times
    void calculateWorkAndOvertime(WorkingTime workingTime);

    // New methods for the web controller functionality
    List<WorkingTime> getAllWorkTimes();     // Retrieve all work time records
    WorkingTime getById(Long id);            // Retrieve a work time record by ID
    void save(WorkingTime workingTime);      // Save or update a work time record
    void delete(Long id);                    // Delete a work time record by ID
    Optional<WorkingTime> findByUserIdAndDate(Long userId, LocalDate date);
    void updateWorkingTime(Long id, Long userId, LocalDateTime checkinTime, LocalDateTime checkoutTime, Float breaktime);
}