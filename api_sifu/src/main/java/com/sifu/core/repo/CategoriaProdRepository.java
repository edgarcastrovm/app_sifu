package com.sifu.core.repo;

import com.sifu.core.utils.entity.CategoriaProd;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProdRepository extends JpaRepository<CategoriaProd, Integer> {
	Optional<CategoriaProd> findByNombre(String nombre);
}