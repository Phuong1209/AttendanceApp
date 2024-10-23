package com.example.demo.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="job_type")
public class JobType {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String jobType_name;




}