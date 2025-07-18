package com.sifu.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.PersonaRepository;
import com.sifu.core.service.PersonaService;
import com.sifu.core.utils.entity.Persona;

@Service
public class PersonaServiceImpl implements PersonaService {

	private static final Logger log = LogManager.getLogger(PersonaServiceImpl.class);
	@Autowired
	private PersonaRepository personaRepository;

	@Override
	public Persona crearPersona(Persona persona) {
		log.info("crearPersona() called");
		// TODO Auto-generated method stub
		return personaRepository.save(persona);
	}

	@Override
	public Persona obtenerPorId(Integer id) {
		log.info("obtenerPorId() called");
		// TODO Auto-generated method stub
		return personaRepository.findById(id).orElse(null);
	}

	@Override
	public Persona actualizarPersona(Integer id, Persona persona) {
		log.info("actualizarPersona() called");
		Persona personaExistente = personaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Persona no encontrado con ID: " + id));

		log.info("Actualizando Persona ID {}: {} {}", id, persona.getNombre(), persona.getApellido());

		personaExistente.setNombre(persona.getNombre());
		personaExistente.setApellido(persona.getApellido()); 
		personaExistente.setCedula(persona.getCedula()); 
		personaExistente.setCelular(persona.getCelular()); 
		personaExistente.setCorreo(persona.getCorreo()); 
		personaExistente.setProvincia(persona.getProvincia().trim());
		personaExistente.setCanton(persona.getCanton().trim());
	    return personaRepository.save(personaExistente);
	}

}
