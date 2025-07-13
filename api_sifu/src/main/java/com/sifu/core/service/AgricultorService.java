package com.sifu.core.service;

import java.util.List;
import java.util.Optional;

import com.sifu.core.utils.dto.dominio.ActualizarClienteDto;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Cliente;


public interface AgricultorService {

	List<Agricultor> obtenerTodas();
	Agricultor crearAgricultor(CrearAgricultorDto registro) ;
	Agricultor obtenerPorId(Integer id);
	Agricultor actualizarAgricultor(Integer id, CrearAgricultorDto agricultorDto);

	Optional<Agricultor> findByPersonaId(Integer personaId);
}
