package com.example.demo.controller;

import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PositionResponse;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.model.Position;
import com.example.demo.service.Position.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;
    private final PositionMapper positionMapper;

    @PostMapping
    public ResponseEntity<PositionResponse> createPosition(@RequestBody PositionDTO positionDTO) {
        Position position = positionMapper.toPosition(positionDTO);
        Position savedPosition = positionService.save(position);
        PositionResponse response = positionMapper.toPositionResponse(savedPosition);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponse> updatePosition(@PathVariable Long id, @RequestBody PositionDTO positionDTO) {
        Optional<Position> optionalPosition = positionService.findById(id);
        if (optionalPosition.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Position position = optionalPosition.get();
        position.setName(positionDTO.getName()); // Update the name field

        Position updatedPosition = positionService.save(position);
        PositionResponse response = positionMapper.toPositionResponse(updatedPosition);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getAllPositions() {
        List<PositionResponse> responses = positionService.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponse> getPositionById(@PathVariable Long id) {
        Optional<Position> optionalPosition = positionService.findById(id);
        return optionalPosition.map(position -> {
            PositionResponse response = positionMapper.toPositionResponse(position);
            return ResponseEntity.ok(response);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{positionId}")
    public ApiResponse<Void> deleteById(@PathVariable Long positionId){
        positionService.delete(positionId);
        return ApiResponse.<Void>builder().build();
    }
}
