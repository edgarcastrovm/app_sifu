package com.sifu.core.service;

import java.util.List;

import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.entity.Agricultor;


public interface AgricultorService {

	List<Agricultor> obtenerTodas();
	Agricultor crearAgricultor(CrearAgricultorDto registro) ;
	Agricultor obtenerPorId(Integer id);
	
}
