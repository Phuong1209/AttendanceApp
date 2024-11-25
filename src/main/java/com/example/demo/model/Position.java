package com.example.demo.model;

import com.example.demo.dto.PositionDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "position")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class Position implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy="positions", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> users;

    public Position(PositionDTO positionDTO){
        this.name = positionDTO.getName();
    }
}