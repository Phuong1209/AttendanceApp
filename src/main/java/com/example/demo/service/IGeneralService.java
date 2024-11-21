package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    T save(T model);

    void remove(Long id);

    void delete(User user);
}
