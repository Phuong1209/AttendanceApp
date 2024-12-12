package com.example.demo.service.Position;

import com.example.demo.dto.request.PositionRequest;
import com.example.demo.dto.response.PositionResponse;
import com.example.demo.model.Position;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface IPositionService {
    PositionResponse create(PositionRequest request);

    List<PositionResponse> getAll();

    void delete(Long position);

    @Transactional
    Optional<Position> findById(Long id);
}