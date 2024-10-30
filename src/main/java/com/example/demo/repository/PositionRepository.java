package com.example.demo.repository;

import com.example.demo.model.Position;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position,Long> {
    List<Position> findByUsers(User user);
}
