package com.example.demo.mapper;

import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.response.PositionResponse;
import com.example.demo.model.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    // Map Position entity to PositionResponse
    public PositionResponse toPositionResponse(Position position) {
        PositionResponse response = new PositionResponse();
        response.setId(position.getId());
        response.setPositionName(position.getName()); // Correctly map name to positionName
        return response;
    }

    // Map PositionDTO to Position entity
    public Position toPosition(PositionDTO dto) {
        Position position = new Position();
        position.setId(dto.getId());
        position.setName(dto.getName()); // Map name field
        return position;
    }

    // Map Position entity to PositionDTO
    public PositionDTO toPositionDTO(Position position) {
        PositionDTO dto = new PositionDTO();
        dto.setId(position.getId());
        dto.setName(position.getName()); // Map name field
        return dto;
    }
}
