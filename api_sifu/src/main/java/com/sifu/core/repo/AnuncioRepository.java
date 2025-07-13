package com.sifu.core.repo;

import com.sifu.core.utils.entity.Anuncio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Integer> {

	List<Anuncio> findByAgricultorId(Integer agricultorId);
}