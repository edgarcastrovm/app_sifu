package com.sifu.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class AGenericService<T, ID> implements IGenericService<T, ID> {
    protected final JpaRepository<T, ID> repo;
    
    private static final Logger log = LogManager.getLogger(AGenericService.class);

    public AGenericService(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    @Override
    public List<T> findAll() {
    	log.info("findAll() called");
        var data = repo.findAll();
        return data;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
    	log.info("findAll() called");
        var data = repo.findAll(pageable);
        return data;
    }

    @Override
    public Optional<T> findById(ID id) {
    	log.info("findById() called");
        var data = repo.findById(id);

        return data.isPresent() ? data : Optional.empty();
    }

    @Override
    public T save(T entity) {
    	log.info("save() called");
        var data = repo.save(entity);
        return data;
    }

    @Override
    public Boolean deleteById(ID id) {
    	log.info("deleteById() called");
        Boolean deleted = false;
        try {
            repo.deleteById(id);
        } catch (Exception e) {

        }
        return deleted;
    }
}
