package com.example.demo.mapper;

import org.mapstruct.Mapper;
import com.example.demo.dto.request.PositionRequest;
import com.example.demo.dto.response.PositionResponse;

import com.example.demo.model.Position;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    Position toPosition(PositionRequest request);

    PositionResponse toPositionResponse(Position position);


}
