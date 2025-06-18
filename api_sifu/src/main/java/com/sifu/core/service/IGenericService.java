package com.sifu.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


interface IGenericService<T, ID> {
    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(ID id);

    T save(T entity);

    Boolean deleteById(ID id);
}