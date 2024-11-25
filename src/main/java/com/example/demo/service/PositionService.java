package com.example.demo.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import com.example.demo.dto.request.PositionRequest;
import com.example.demo.dto.response.PositionResponse;
//import com.example.demo.entity.Permission;
import com.example.demo.model.Position;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.repository.IPositionRepository;
//import com.example.demo.repository.PermissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionService {
    IPositionRepository positionRepository;
    PositionMapper positionMapper;
    public PositionResponse create(PositionRequest request){
        Position n = new Position();
        n.setName(request.getPositionName());
        Position savedPosition = positionRepository.save(n);
        PositionResponse positionResponse = positionMapper.toPositionResponse(savedPosition);
        return positionResponse;
    }
    public List<PositionResponse> getAll(){
        return positionRepository.findAll()
                .stream().map(positionMapper::toPositionResponse).toList();
    }
    public void delete(Long position){
        positionRepository.deleteById(position);
    }
}
