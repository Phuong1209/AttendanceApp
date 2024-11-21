package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Position;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByName(String name);
    List<Position> findByUsers(User user);
}
