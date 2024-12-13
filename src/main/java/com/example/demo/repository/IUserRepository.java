package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IUserRepository extends JpaRepository <User, Long> {
    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String username);

    List<User> findByDepartments(Department department);

    //Security
    User findFirstByUserName(String username);

    Long id(Long id);

}
