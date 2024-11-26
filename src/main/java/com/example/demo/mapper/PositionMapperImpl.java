//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo.mapper;

import com.example.demo.dto.response.PositionResponse;
import com.example.demo.model.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperImpl extends PositionMapper {
    public PositionMapperImpl() {
    }

    public PositionResponse toPositionResponse(Position Position) {
        if (Position == null) {
            return null;
        } else {
            PositionResponse.PositionResponseBuilder positionResponse = PositionResponse.builder();
            positionResponse.id(Position.getId());
            return positionResponse.build();
        }
    }
}