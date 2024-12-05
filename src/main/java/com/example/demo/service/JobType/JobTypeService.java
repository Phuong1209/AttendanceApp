package com.example.demo.service.JobType;

import com.example.demo.dto.JobTypeDTO;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.repository.IJobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobTypeService implements IJobTypeService {
    @Autowired
    private IJobTypeRepository jobTypeRepository;

    @Override
    public Iterable<JobType> findAll() {
        return jobTypeRepository.findAll();
    }

    @Override
    public Optional<JobType> findById(Long id) {
        return jobTypeRepository.findById(id);
    }

    @Override
    public void save(JobTypeDTO jobTypeDTO) {

    }

    /*@Override
    public void deleteById(Long id) {

    }*/

    @Override
    public JobType save(JobType model) {
        return jobTypeRepository.save(model);
    }

   /* @Override
    public void remove(Long id) {
        jobTypeRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {

    }*/
   @Override
   public void remove(Long id) {
       if (jobTypeRepository.existsById(id)) {
           jobTypeRepository.deleteById(id);
       } else {
           throw new IllegalArgumentException("JobType with ID " + id + " does not exist.");
       }
   }

    @Override
    public void delete(User user) {

    }
}
