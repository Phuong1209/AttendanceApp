package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByWorkTime(WorkTime workTime);
    List<Task> findByProject(Project project);
    Optional<Task> findById(Long id);
}
