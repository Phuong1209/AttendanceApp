package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "position")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String positionName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
