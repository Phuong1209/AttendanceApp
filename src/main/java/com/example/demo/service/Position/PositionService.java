package com.example.demo.service.Position;

import jakarta.transaction.Transactional;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionService implements IPositionService {
    IPositionRepository positionRepository;
    PositionMapper positionMapper;
    @Override
    public PositionResponse create(PositionRequest request){
        Position n = new Position();
        n.setName(request.getPositionName());
        Position savedPosition = positionRepository.save(n);
        PositionResponse positionResponse = positionMapper.toPositionResponse(savedPosition);
        return positionResponse;
    }
    // fix get all
    @Override
    public List<PositionResponse> getAll() {
        return positionRepository.findAll()
                .stream()
                .map(positionMapper::toPositionResponse) // Map to PositionResponse
                .collect(Collectors.toList());
    }
    @Override
    public void delete(Long position){
        positionRepository.deleteById(position);
    }
    @Transactional
    @Override
    public Optional<Position> findById(Long id) {
        return positionRepository.findById(id); // Assuming positionRepository is already defined.
    }


    public Position save(Position position) {
        return positionRepository.save(position);
    }
}