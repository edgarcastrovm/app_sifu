package com.sifu.core.service;

import com.sifu.core.utils.entity.Persona;

public interface PersonaService {
	
	Persona crearPersona(Persona persona);
	Persona obtenerPorId(Integer id);
	Persona actualizarPersona(Integer id, Persona persona);

}
