package com.example.demo.repository;

import com.example.demo.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkTimeRepository  extends JpaRepository<WorkingTime, Long> {
    List<WorkingTime> findByUserId(Long userId);

    @Query("SELECT w FROM WorkingTime w WHERE w.user_id = :userId AND w.date = :date")
    Optional<WorkingTime> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    // Custom query to find the latest WorkingTime for check-out
    @Query("SELECT w FROM WorkingTime w WHERE w.user_id = :userId ORDER BY w.checkin_time DESC")
    WorkingTime findLatestByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM WorkingTime w WHERE w.user_id = :userId ORDER BY w.checkin_time DESC")
    Optional<WorkingTime> findFirstByUserIdOrderByCheckin_timeDesc(Long userId);

      @Query("SELECT w FROM WorkingTime w WHERE w.user_id = :userId AND w.date = :date AND w.checkout_time IS NULL ORDER BY w.checkin_time DESC")
    Optional<WorkingTime>  findLatestUnclosedByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT w FROM WorkingTime w WHERE w.user_id = :userId AND w.date = :date AND w.checkout_time IS NULL")
    Optional<WorkingTime> findUnclosedByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT w FROM WorkingTime w WHERE w.user_id = :userId AND w.checkout_time IS NULL ORDER BY w.checkin_time DESC")
    Optional<WorkingTime> findLatestUnclosedByUserIdOrderByCheckin_timeDesc(@Param("userId") Long userId);


}
