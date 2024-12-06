package com.example.demo.service.WorkTime;

import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.Task;
import com.example.demo.model.WorkTime;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//12/05 added
@Repository
public interface IWorkTimeService {
    List<WorkTimeDTO> getAllWorkTime();
    WorkTimeDTO getWorkTimeById(Long id);
    Iterable<WorkTime> findAll();
    Optional<WorkTime> findById(Long id);
    WorkTime save(WorkTime model);
    void remove(Long id);
    Iterable<WorkTime> findAllByUserId(Long userId);

    List<Task> getTaskByWorkTime(Long id);

    Iterable<WorkTimeDTO> getWorkTimeForUserAndMonth(Long id, int year, int month);
}
/*
public interface IWorkTimeService extends IGeneralService<WorkTime> {
    List<WorkTimeDTO> getAllWorkTime();
    List<Task> getTaskByWorkTime(Long workTimeId);
    WorkTimeDTO getWorkTimeById(Long workTimeid);

    Iterable<WorkTime> findAllByUserId(Long id);
*/

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
