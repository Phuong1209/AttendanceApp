//Mai Huong

package com.example.demo.service.JobType;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.JobTypeDTO;
import com.example.demo.model.JobType;
import com.example.demo.service.IGeneralService;

import java.util.List;
import java.util.Optional;


public interface IJobTypeService extends IGeneralService<JobType> {
    List<JobTypeDTO> getAllJobType();

    Iterable<JobType> findAll();
    Optional<JobType> findById(Long id); // Fetch DTO by ID
    void save(JobTypeDTO jobTypeDTO); // Save DTO
    /*void deleteById(Long id); // Delete by ID*/
    void remove(Long id);
}