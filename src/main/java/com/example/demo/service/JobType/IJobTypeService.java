package com.example.demo.service.JobType;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.JobTypeDTO;
import com.example.demo.model.JobType;
import com.example.demo.service.IGeneralService;

import java.util.List;


public interface IJobTypeService extends IGeneralService<JobType> {
    List<JobTypeDTO> getAllJobType();
}