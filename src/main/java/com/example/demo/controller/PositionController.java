package com.example.demo.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.request.PositionRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PositionResponse;
import com.example.demo.service.PositionService;

import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PositionController {
    PositionService positionService;
    @PostMapping
    public ApiResponse<PositionResponse> create(@RequestBody PositionRequest request){
        return ApiResponse.<PositionResponse>builder()
                .result(positionService.create(request))
                .build();
    }
    @GetMapping
    public ApiResponse<List<PositionResponse>> getAll(){
        return ApiResponse.<List<PositionResponse>>builder()
                .result(positionService.getAll())
                .build();
    }
    @DeleteMapping("/{positionId}")
    public ApiResponse<Void> deleteById(@PathVariable Long positionId){
        positionService.delete(positionId);
        return ApiResponse.<Void>builder().build();
    }
}
