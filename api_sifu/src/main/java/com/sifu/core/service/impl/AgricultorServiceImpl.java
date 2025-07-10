package com.sifu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.AgricultorRepository;
import com.sifu.core.repo.RolRepository;
import com.sifu.core.service.AgricultorService;
import com.sifu.core.service.PersonaService;
import com.sifu.core.service.UsuarioService;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.dto.dominio.CrearClienteDto;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Rol;
import com.sifu.core.utils.entity.Usuario;


@Service
public class AgricultorServiceImpl implements AgricultorService{
	
	@Autowired
	private AgricultorRepository agricultorRepository;
	@Autowired
	private PersonaService personaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Agricultor> obtenerTodas() {
		// TODO Auto-generated method stub
		return agricultorRepository.findAll();
	}

	@Override
	public Agricultor crearAgricultor(CrearAgricultorDto registro) {
		Persona persona = new Persona();
        persona.setNombre(registro.getNombre());
        persona.setApellido(registro.getApellido());
        persona.setCorreo(registro.getCorreo());
        persona.setCedula(registro.getCedula());
        persona.setCelular(registro.getCelular());
    	Persona personaGuardado = personaService.crearPersona(persona);
		
    	Usuario usuario = new Usuario();
        usuario.setAlias(registro.getAlias());
        usuario.setClave(passwordEncoder.encode(registro.getClave())); 
        usuario.setPersona(personaGuardado);
        usuario.setActivo(true);
        
        Optional<Rol> rolAgricultor = rolRepository.findByNombre("AGRICULTOR");
        if(! rolAgricultor.isPresent()) {
        	return null;
        }
        usuario.setRol(rolAgricultor.get());
	    usuarioService.crearUsuario(usuario);
		
		Agricultor agricultor = new Agricultor();
		agricultor.setPersona(personaGuardado);
		
		return agricultorRepository.save(agricultor);
	}

	@Override
	public Agricultor obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return agricultorRepository.findById(id).orElse(null);
	}
	
	

}
