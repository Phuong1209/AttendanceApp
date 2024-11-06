package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Long> {
    boolean existsByName(String name);
    @Query("SELECT d.users FROM Department d WHERE d.id = :departmentId")
    Set<User> findUsersByDepartmentId(@Param("departmentId") Long departmentId);
    @Query("SELECT d.jobTypes FROM Department d WHERE d.id = :departmentId")
    Set<JobType> findJobTypesByDepartmentId(@Param("departmentId") Long jobTypeId);
}
