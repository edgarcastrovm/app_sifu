package com.sifu.core.repo;

import com.sifu.core.utils.entity.CategoriaProd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaProdRepository extends JpaRepository<CategoriaProd, Integer> {
    Optional<CategoriaProd> findByNombre(String nombre);
}