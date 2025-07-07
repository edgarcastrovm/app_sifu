package com.sifu.core.repo;

import com.sifu.core.utils.entity.DetalleFact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleFactRepository extends JpaRepository<DetalleFact, Integer> {
}