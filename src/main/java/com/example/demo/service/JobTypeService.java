package com.example.demo.service;
import com.example.demo.model.JobType;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.service.IJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobTypeService implements IJobTypeService {
    @Autowired
    private IJobTypeRepository JobTypeRepository;

    @Override
    public List<JobType> findAll() {
        return JobTypeRepository.findAll();  // Sử dụng repository để lấy tất cả JobType
    }

    @Override
    public Optional<JobType> findById(Long id) {
        return JobTypeRepository.findById(id);  // Tìm JobType theo id
    }

    @Override
    public void save(JobType jobType) {
        JobTypeRepository.save(jobType);  // Lưu JobType vào database
    }

    @Override
    public void remove(Long id) {
        JobTypeRepository.deleteById(id);  // Xóa JobType theo id
    }
}
