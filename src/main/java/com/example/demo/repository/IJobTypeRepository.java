package com.example.demo.repository;

import com.example.demo.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobTypeRepository extends JpaRepository<JobType, Long> {
}