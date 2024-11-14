package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByWorkTime(WorkTime workTime);
}
