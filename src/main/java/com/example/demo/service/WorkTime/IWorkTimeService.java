package com.example.demo.service.WorkTime;

import com.example.demo.model.Task;
import com.example.demo.model.WorkTime;
import com.example.demo.model.dto.ProjectDTO;
import com.example.demo.model.dto.WorkTimeDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Repository
public interface IWorkTimeService extends IGeneralService<WorkTime> {
    List<WorkTimeDTO> getAllWorkTime();
    List<Task> getTaskByWorkTime(Long workTimeId);
    WorkTimeDTO editWorkTime(Long workTimeId, LocalTime checkinTime, LocalTime checkoutTime, Float breakTime, Set<Long> taskIds);



    /*Code TA
    WorkTime checkin(Long userId, LocalDateTime checkinTime);      // Check-in user
    WorkTime checkout(Long userId, LocalDateTime checkoutTime);    // Check-out user
    WorkTime updateBreaktime(Long userId, Float breaktimeHours); // Set/update breaktime
    List<WorkTime> getWorkTimesByUser(Long userId);             // Retrieve user's work times
    void calculateWorkAndOvertime(WorkTime workTime);

    // New methods for the web controller functionality
    List<WorkTime> getAllWorkTimes();     // Retrieve all work time records
    WorkTime getById(Long id);            // Retrieve a work time record by ID
    void save(WorkTime workTime);      // Save or update a work time record
    void delete(Long id);                    // Delete a work time record by ID
    Optional<WorkTime> findByUserIdAndDate(Long userId, LocalDate date);
    void updateWorkingTime(Long id, Long userId, LocalDateTime checkinTime, LocalDateTime checkoutTime, Float breaktime);
*/
}