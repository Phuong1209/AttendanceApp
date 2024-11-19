package com.example.demo.repository;

import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByWorkingTime(WorkingTime workingTime);

    List<Task> findByProject(Project project);
}
