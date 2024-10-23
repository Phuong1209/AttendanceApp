package com.example.demo.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="jobtype")
public class Jobtype {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String jobType_name;




}