/*
//Code P
package com.example.demo.controller;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.*;
import com.example.demo.repository.IUserRepository;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.User.IUserService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/worktime")
public class WorkTimeController {
    @Autowired
    private IWorkTimeService workTimeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRepository userRepository;

    //show list
    @GetMapping
    public ResponseEntity<List<WorkTimeDTO>> getAllWorkTimes() {
        List<WorkTimeDTO> workTimeDTOS = workTimeService.getAllWorkTime();
        return ResponseEntity.ok(workTimeDTOS);
    }

    //show by id (new)
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkTimeById(@PathVariable Long id) {
        WorkTimeDTO workTimeDTO = workTimeService.getWorkTimeById(id);
        if (workTimeDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WorkTime not found.");
        }
        return ResponseEntity.ok(workTimeDTO);
    }

    //get task list
    @GetMapping("getTask/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        List<Task> tasks = workTimeService.getTaskByWorkTime(id);
        return ResponseEntity.ok().body(tasks);
    }

    //create (old)
    @PostMapping("")
    public ResponseEntity<?> createWorkTime(@RequestBody Map<String, Object> requestBody) {
        try {
            // Parse request body
            LocalDate date = LocalDate.parse((String) requestBody.get("date"));

            // Validate date: cannot be more than today + 1
            if (date.isAfter(LocalDate.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date cannot be in the future beyond today or tomorrow.");
            }

            LocalTime checkinTime = LocalTime.parse((String) requestBody.get("checkinTime"));
            LocalTime checkoutTime = LocalTime.parse((String) requestBody.get("checkoutTime"));
            Float breakTime = Float.parseFloat(requestBody.get("breakTime").toString());
            Map<String, Object> userMap = (Map<String, Object>) requestBody.get("user");
            Long userId = Long.valueOf(userMap.get("id").toString());

            // Validate that checkinTime and checkoutTime are in 10-minute intervals
            if (!isValidTimeInterval(checkinTime) || !isValidTimeInterval(checkoutTime)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Times must be in 10-minute intervals.");
            }

            // Fetch User entity
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            User user = optionalUser.get();

            // Calculate workTime and overTime
            Duration duration = Duration.between(checkinTime, checkoutTime);
            float workTimeHours = duration.toMinutes() / 60.0f - breakTime;
            float overTimeHours = workTimeHours > 8 ? workTimeHours - 8 : 0;

            // Create and save WorkTime
            WorkTime workTime = WorkTime.builder()
                    .date(date)
                    .checkinTime(checkinTime)
                    .checkoutTime(checkoutTime)
                    .breakTime(breakTime)
                    .workTime(workTimeHours)
                    .overTime(overTimeHours)
                    .user(user)
                    .build();
            WorkTime savedWorkTime = workTimeService.save(workTime);

            // Construct response body
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedWorkTime.getId());
            response.put("date", savedWorkTime.getDate().toString());
            response.put("checkinTime", savedWorkTime.getCheckinTime().toString());
            response.put("checkoutTime", savedWorkTime.getCheckoutTime().toString());
            response.put("breakTime", savedWorkTime.getBreakTime());
            response.put("workTime", savedWorkTime.getWorkTime());
            response.put("overTime", savedWorkTime.getOverTime());
            response.put("user", Map.of("id", user.getId()));

            // Return 201 Created with response body
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            // Handle exceptions gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to check if the time is in 10-minute intervals
    private boolean isValidTimeInterval(LocalTime time) {
        return time.getMinute() % 10 == 0; // Check if minutes are divisible by 10
    }


    //create
    */
/*@PostMapping("")
    public ResponseEntity<?> createWorkTime(@RequestBody Map<String, Object> requestBody) {
        try {
            // Fetch the logged-in user's username
            String username = SecurityUtil.getSessionUser();
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
            }

            // Fetch User entity by username
            User user = userRepository.findByUserName(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            // Parse request body
            LocalDate date = LocalDate.parse((String) requestBody.get("date"));
            LocalTime checkinTime = LocalTime.parse((String) requestBody.get("checkinTime"));
            LocalTime checkoutTime = LocalTime.parse((String) requestBody.get("checkoutTime"));
            Float breakTime = Float.parseFloat(requestBody.get("breakTime").toString());

            // Calculate workTime and overTime
            Duration duration = Duration.between(checkinTime, checkoutTime);
            float workTimeHours = duration.toMinutes() / 60.0f - breakTime;
            float overTimeHours = workTimeHours > 8 ? workTimeHours - 8 : 0;

            // Create and save WorkTime
            WorkTime workTime = WorkTime.builder()
                    .date(date)
                    .checkinTime(checkinTime)
                    .checkoutTime(checkoutTime)
                    .breakTime(breakTime)
                    .workTime(workTimeHours)
                    .overTime(overTimeHours)
                    .user(user)
                    .build();
            WorkTime savedWorkTime = workTimeService.save(workTime);

            // Construct response body
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedWorkTime.getId());
            response.put("date", savedWorkTime.getDate().toString());
            response.put("checkinTime", savedWorkTime.getCheckinTime().toString());
            response.put("checkoutTime", savedWorkTime.getCheckoutTime().toString());
            response.put("breakTime", savedWorkTime.getBreakTime());
            response.put("workTime", savedWorkTime.getWorkTime());
            response.put("overTime", savedWorkTime.getOverTime());
            response.put("user", Map.of("id", user.getId()));

            // Return 201 Created with response body
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            // Handle exceptions gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }*//*


    //edit (old)
    @PutMapping("/{id}")
    public ResponseEntity<?> editWorkTime(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            // Fetch the existing WorkTime record
            Optional<WorkTime> optionalWorkTime = workTimeService.findById(id);
            if (optionalWorkTime.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WorkTime not found.");
            }
            WorkTime existingWorkTime = optionalWorkTime.get();

            // Parse and validate editable fields
            if (requestBody.containsKey("date")) {
                LocalDate date = LocalDate.parse((String) requestBody.get("date"));
                // Validate date: cannot be more than today + 1
                if (date.isAfter(LocalDate.now())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date cannot be in the future beyond today or tomorrow.");
                }
                existingWorkTime.setDate(date);
            }
            if (requestBody.containsKey("checkinTime")) {
                LocalTime checkinTime = LocalTime.parse((String) requestBody.get("checkinTime"));
                if (!isValidTimeInterval(checkinTime)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Checkin time must be in 10-minute intervals.");
                }
                existingWorkTime.setCheckinTime(checkinTime);
            }
            if (requestBody.containsKey("checkoutTime")) {
                LocalTime checkoutTime = LocalTime.parse((String) requestBody.get("checkoutTime"));
                if (!isValidTimeInterval(checkoutTime)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Checkout time must be in 10-minute intervals.");
                }
                existingWorkTime.setCheckoutTime(checkoutTime);
            }
            if (requestBody.containsKey("breakTime")) {
                existingWorkTime.setBreakTime(Float.parseFloat(requestBody.get("breakTime").toString()));
            }

            // Recalculate workTime and overTime
            Duration duration = Duration.between(existingWorkTime.getCheckinTime(), existingWorkTime.getCheckoutTime());
            float workTimeHours = duration.toMinutes() / 60.0f - existingWorkTime.getBreakTime();
            existingWorkTime.setWorkTime(workTimeHours);
            existingWorkTime.setOverTime(workTimeHours > 8 ? workTimeHours - 8 : 0);

            // Save updated WorkTime record
            WorkTime updatedWorkTime = workTimeService.save(existingWorkTime);

            // Map WorkTime and Tasks to DTO
            WorkTimeDTO workTimeDTO = new WorkTimeDTO();
            workTimeDTO.setId(updatedWorkTime.getId());
            workTimeDTO.setDate(updatedWorkTime.getDate());
            workTimeDTO.setCheckinTime(updatedWorkTime.getCheckinTime());
            workTimeDTO.setCheckoutTime(updatedWorkTime.getCheckoutTime());
            workTimeDTO.setBreakTime(updatedWorkTime.getBreakTime());
            workTimeDTO.setWorkTime(updatedWorkTime.getWorkTime());
            workTimeDTO.setOverTime(updatedWorkTime.getOverTime());

            // Map User to UserDTO
            if (updatedWorkTime.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(existingWorkTime.getUser().getId());
                userDTO.setUserName(existingWorkTime.getUser().getUserName());
                workTimeDTO.setUser(userDTO);
            }

            // Map Tasks to TaskDTOs
            Set<TaskDTO> taskDTOs = updatedWorkTime.getTasks().stream().map(task -> {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                return taskDTO;
            }).collect(Collectors.toSet());

            // Add tasks to WorkTimeDTO
            workTimeDTO.setTasks(taskDTOs);

            // Return response
            return ResponseEntity.ok(workTimeDTO);

        } catch (Exception e) {
            // Handle exceptions gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
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
*/
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


    //Code TA
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
}*/
