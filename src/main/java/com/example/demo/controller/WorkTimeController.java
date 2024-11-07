package com.example.demo.controller;

import com.example.demo.model.WorkingTime;
import com.example.demo.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
            LocalDate checkinDate = parsedCheckinTime.toLocalDate();
            // Validate if there is already a closed record for this date
            if (workTimeService.hasRecordForDate(userId, checkinDate)) {
                return ResponseEntity.badRequest().body("Error: A record already exists for this user on " + checkinDate);
            }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkingTime(@PathVariable Long id) {
        try {
            workTimeService.delete(id);
            return ResponseEntity.ok("Working time record deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateWorkingTime(
            @RequestParam Long id,// Using @RequestParam for ID
            @RequestParam Long userId,
            @RequestParam String checkinTime,
            @RequestParam String checkoutTime,
            @RequestParam Float breaktime) {
        try {
            LocalDateTime parsedCheckinTime = LocalDateTime.parse(checkinTime);
            LocalDateTime parsedCheckoutTime = LocalDateTime.parse(checkoutTime);

            workTimeService.updateWorkingTime(id, userId, parsedCheckinTime, parsedCheckoutTime, breaktime);
            return ResponseEntity.ok("Working time record updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

