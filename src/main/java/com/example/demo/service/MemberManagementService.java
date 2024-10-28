package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.MemberManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class MemberManagementService implements IMemberManagementService {
    @Autowired
    private MemberManagementRepository memberManagementRepository;

    @Override
    public Iterable<User> findAll() {
        return memberManagementRepository.findAll();
    }

    @Override
    public  Optional<User> findById(Long id) {
        return memberManagementRepository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return memberManagementRepository.save(user);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        memberManagementRepository.deleteById(id);

    }


}
