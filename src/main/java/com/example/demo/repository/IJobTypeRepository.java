package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.Task;
import com.example.demo.model.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobTypeRepository extends JpaRepository<JobType, Long> {
}