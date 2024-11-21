package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;
import com.example.demo.model.Department;


import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
    List<User> findByDepartments(Department department);
}
