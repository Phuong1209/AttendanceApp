package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository <User, Long> {
    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String username);
    List<User> findByDepartments(Department department);
    //Security
    User findFirstByUserName(String username);
}
