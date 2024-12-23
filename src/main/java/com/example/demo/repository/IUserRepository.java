//Thu Phuong

package com.example.demo.repository;

import com.example.demo.model.*;
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

    //show list WorkTime
    @Query("SELECT u.workTimes FROM User u WHERE u.id = :userId")
    Set<WorkTime> findWorkTimesByUser(@Param("userId") Long userId);

}
