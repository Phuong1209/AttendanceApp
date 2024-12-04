package com.example.demo.repository;

import com.example.demo.model.Position;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByUsers(User user);
}
