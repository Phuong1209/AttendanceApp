package com.example.demo.repository;

import com.example.demo.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobtypeRepository extends JpaRepository<JobType, Long> {
}
