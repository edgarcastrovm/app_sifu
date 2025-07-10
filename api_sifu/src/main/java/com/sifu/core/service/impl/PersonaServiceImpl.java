package com.sifu.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.PersonaRepository;
import com.sifu.core.service.PersonaService;
import com.sifu.core.utils.entity.Persona;

@Service
public class PersonaServiceImpl implements PersonaService{

	@Autowired
	private PersonaRepository personaRepository;

	@Override
	public Persona crearPersona(Persona persona) {
		// TODO Auto-generated method stub
		return personaRepository.save(persona);
	}
	
	
	
}
