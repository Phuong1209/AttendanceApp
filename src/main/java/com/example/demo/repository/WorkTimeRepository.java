package com.example.demo.repository;

import com.example.demo.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkTimeRepository  extends JpaRepository<WorkingTime, Long> {
    List<WorkingTime> findByUserId(Long userId);

    // Custom query to find the latest WorkingTime for check-out
    @Query("SELECT w FROM WorkingTime w WHERE w.user_id = :userId ORDER BY w.checkin_time DESC")
    WorkingTime findLatestByUserId(@Param("userId") Long userId);

}
