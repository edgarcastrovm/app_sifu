package com.sifu.core.repo;

import com.sifu.core.utils.entity.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Optional<Cliente> findByPersonaId(Integer personaId);
}