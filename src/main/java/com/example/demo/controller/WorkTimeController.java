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
@RequestMapping("/worktime")
public class WorkTimeController {

    @Autowired
    private WorkTimeService workTimeService;

    // Check-in endpoint
    @PostMapping("/checkin")
    public ResponseEntity<String> checkin(@RequestParam Long userId, @RequestParam String checkinTime) {
        try {
// Convert String to LocalDateTime
            LocalDateTime parsedCheckinTime = LocalDateTime.parse(checkinTime);
            workTimeService.checkin(userId, parsedCheckinTime);
            return ResponseEntity.ok("Check-in successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error:"+ e.getMessage());
        }
    }

    // Check-out endpoint
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestParam Long userId, @RequestParam String checkoutTime) {
        try {
            LocalDateTime parsedCheckoutTime = LocalDateTime.parse(checkoutTime);
            workTimeService.checkout(userId, parsedCheckoutTime);
            return ResponseEntity.ok("Check-out successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update break time
    @PostMapping("/breaktime")
    public ResponseEntity<String> updateBreaktime(@RequestParam Long userId, @RequestParam Float  breaktime) {
        try {
            workTimeService.updateBreaktime(userId, breaktime);
            return ResponseEntity.ok("Break time updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Fetch all working times for a user
    @GetMapping("/user/{userId}")
    public List<WorkingTime> getWorkingTimes(@PathVariable Long userId) {
        return workTimeService.getWorkTimesByUser(userId);
    }
}

