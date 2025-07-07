package com.sifu.core.repo;

import com.sifu.core.utils.entity.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgricultorRepository extends JpaRepository<Agricultor, Integer> {
}