package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor

public class JobTypeDTO {
    private Long id;
    private String name;

    //constructor (to map)
    public JobTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    //constructor (map edit Department)
    public JobTypeDTO(Long id) {
    }
}