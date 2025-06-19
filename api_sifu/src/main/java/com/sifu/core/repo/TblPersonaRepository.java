package com.sifu.core.repo;

import com.sifu.core.utils.entity.TblPersona;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TblPersonaRepository extends JpaRepository<TblPersona, Integer> {
    Optional<TblPersona> findByPerIdentificacion(@Size(max = 20) String perIdentificacion);
}