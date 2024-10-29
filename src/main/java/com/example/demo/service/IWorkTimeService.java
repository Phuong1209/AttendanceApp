package com.example.demo.service;

import com.example.demo.model.WorkingTime;

import java.time.LocalDateTime;
import java.util.List;

public interface IWorkTimeService {
    WorkingTime checkin(Long userId, LocalDateTime checkinTime);      // Check-in user
    WorkingTime checkout(Long userId, LocalDateTime checkoutTime);    // Check-out user
    WorkingTime updateBreaktime(Long userId, LocalDateTime breaktime); // Set/update breaktime
    List<WorkingTime> getWorkTimesByUser(Long userId);             // Retrieve user's work times
    void calculateWorkAndOvertime(WorkingTime workingTime);
}