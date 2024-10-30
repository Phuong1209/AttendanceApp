package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    List<Department> findByUsers(User user);
}
