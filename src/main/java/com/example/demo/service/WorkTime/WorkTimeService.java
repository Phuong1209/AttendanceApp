package com.example.demo.service.WorkTime;

import com.example.demo.model.WorkTime;
import com.example.demo.repository.IWorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkTimeService implements IWorkTimeService {
    @Autowired
    private IWorkTimeRepository workTimeRepositoryRepository;

    @Override
    public Iterable<WorkTime> findAll() {
        return workTimeRepositoryRepository.findAll();
    }
    @Override
    public Optional<WorkTime> findById(Long id) {
        return workTimeRepositoryRepository.findById(id);
    }

    @Override
    public WorkTime save(WorkTime model) {
        return workTimeRepositoryRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        workTimeRepositoryRepository.deleteById(id);
    }
}
