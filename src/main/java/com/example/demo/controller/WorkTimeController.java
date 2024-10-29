package com.example.demo.controller;

import com.example.demo.model.WorkingTime;
import com.example.demo.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/worktimes")
public class WorkTimeController {

    @Autowired
    private WorkTimeService workingTimeService;

    // Check-in endpoint
    @PostMapping("/checkin")
    public ResponseEntity<String> checkin(@RequestParam Long userId, @RequestParam LocalDateTime checkinTime) {
        try {
            workingTimeService.checkin(userId, checkinTime);
            return ResponseEntity.ok("Check-in successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Check-out endpoint
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestParam Long userId, @RequestParam LocalDateTime checkoutTime) {
        try {
            workingTimeService.checkout(userId, checkoutTime);
            return ResponseEntity.ok("Check-out successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update break time
    @PostMapping("/breaktime")
    public ResponseEntity<String> updateBreaktime(@RequestParam Long userId, @RequestParam LocalDateTime breaktime) {
        workingTimeService.updateBreaktime(userId, breaktime);
        return ResponseEntity.ok("Breaktime updated successfully");
    }

    // Fetch all working times for a user
    @GetMapping("/user/{userId}")
    public List<WorkingTime> getWorkingTimes(@PathVariable Long userId) {
        return workingTimeService.getWorkTimesByUser(userId);
    }
}

