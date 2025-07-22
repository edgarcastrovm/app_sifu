package com.sifu.core.repo;

import com.sifu.core.utils.entity.Producto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	Optional<Producto> findByNombre(String nombre);
}