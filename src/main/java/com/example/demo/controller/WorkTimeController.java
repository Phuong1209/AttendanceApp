package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.model.dto.WorkTimeDTO;
import com.example.demo.service.User.IUserService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/worktime")
public class WorkTimeController {
    @Autowired
    private IWorkTimeService workTimeService;
    @Autowired
    private IUserService userService;


    //show list
    @GetMapping
    public ResponseEntity<?> getAllWorkTimes() {
        return ResponseEntity.ok().body(workTimeService.findAll());
    }

    //show by id
    @GetMapping("/{id}")
    public ResponseEntity<WorkTime> getAllUserByWorkTime(@PathVariable Long id) {
        Optional<WorkTime> workTimeOptional = workTimeService.findById(id);
        return workTimeOptional.map(workTime -> new ResponseEntity<>(workTime, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //get task list
    @GetMapping("getTask/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        List<Task> tasks = workTimeService.getTaskByWorkTime(id);
        return ResponseEntity.ok().body(tasks);
    }

    //create (new)
    @PostMapping("")
    public ResponseEntity<WorkTime> createWorkTime(@RequestBody WorkTimeDTO workTimeDTO) {
        WorkTime newWorkTime = new WorkTime();
        newWorkTime.setCheckinTime(workTimeDTO.getCheckinTime());
        newWorkTime.setCheckoutTime(workTimeDTO.getCheckoutTime());
        newWorkTime.setBreakTime(workTimeDTO.getBreakTime());

        //caculate workTime overTime
        Duration duration = Duration.between(newWorkTime.getCheckinTime(), newWorkTime.getCheckoutTime());
        float workTimeHours = duration.toMinutes() / 60.0f - newWorkTime.getBreakTime();

        //set workTime and overTime
        newWorkTime.setWorkTime(workTimeHours);
        newWorkTime.setOverTime(workTimeHours > 8 ? workTimeHours - 8 : 0);

        // Fetch user from database using user ID
/*        Optional<User> optionalUser = userService.findById(workTimeDTO.getUser().getId());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        newWorkTime.setUser(optionalUser.get());*/

        //save new workTime
        workTimeService.save(newWorkTime);
        return new ResponseEntity<>(newWorkTime, HttpStatus.CREATED);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<WorkTime> editWorkTime(@PathVariable Long id, @RequestBody WorkTime workTime) {
        Optional<WorkTime> workTimeOptional = workTimeService.findById(id);
        if (!workTimeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        workTime.setId(id);
        workTimeService.save(workTime);
        return new ResponseEntity<>(workTime, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkTime(@PathVariable Long id) {
        Optional<WorkTime> workTimeOptional = workTimeService.findById(id);
        if (!workTimeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        workTimeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

//Code TuanAnh
/*    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteWorkingTime(@PathVariable Long id) {
    try {
        workTimeService.delete(id);
        return ResponseEntity.ok("Working time record deleted successfully");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}


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

@PutMapping("/update")
public ResponseEntity<String> updateWorkingTime(
        @RequestParam Long id,
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
}*/
