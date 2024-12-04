package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.Optional;

@Repository
public interface IDepartmentRepository extends JpaRepository <Department, Long> {
    List<Department> findByUsers(User user);

    //show list JobType
    @Query("SELECT d.jobTypes FROM Department d WHERE d.id = :departmentId")
    Set<JobType> findJobTypesByDepartment(@Param("departmentId") Long departmentId);

    //show list user
    @Query("SELECT d.users FROM Department d WHERE d.id = :departmentId")
    Set<User> findUsersByDepartment(@Param("departmentId") Long departmentId);
}