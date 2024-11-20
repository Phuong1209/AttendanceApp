package com.example.demo.repository;

import com.example.demo.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPositionRepository extends JpaRepository <Position, Long> {
    Position findByName(String name);
}
