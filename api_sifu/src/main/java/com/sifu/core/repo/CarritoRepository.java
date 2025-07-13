package com.sifu.core.repo;

import com.sifu.core.utils.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByCliente_Id(Integer clienteId);
}