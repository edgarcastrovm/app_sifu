package com.sifu.core.repo;

import com.sifu.core.utils.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByCedula(String cedula);
}