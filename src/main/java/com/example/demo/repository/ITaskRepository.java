package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByWorkTime(WorkTime workTime);

    List<Task> findByProject(Project project);

    Optional<Task> findById(Long id);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.workTime.id = :workTimeId AND t.workTime.date = :date")
    int countByWorkTimeAndDate(@Param("workTimeId") Long workTimeId, @Param("date") LocalDate date);

}
