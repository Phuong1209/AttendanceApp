package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartmentRepository extends JpaRepository <Department, Long> {
    List<Department> findByUsers(User user);
}