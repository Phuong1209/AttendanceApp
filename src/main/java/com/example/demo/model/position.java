package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "position")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String positionName;
}
