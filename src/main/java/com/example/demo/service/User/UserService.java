package com.example.demo.service.User;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User model) {
        return userRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }


}
