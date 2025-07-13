package com.sifu.core.service.impl;

import com.sifu.core.repo.AgricultorRepository;
import com.sifu.core.repo.RolRepository;
import com.sifu.core.service.AgricultorService;
import com.sifu.core.service.PersonaService;
import com.sifu.core.service.UsuarioService;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Cliente;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Rol;
import com.sifu.core.utils.entity.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AgricultorServiceImpl implements AgricultorService {

    private static final Logger log = LogManager.getLogger(AgricultorServiceImpl.class);
    @Autowired
    private AgricultorRepository agricultorRepository;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Agricultor> obtenerTodas() {
        // TODO Auto-generated method stub
        return agricultorRepository.findAll();
    }

    @Override
    public Agricultor crearAgricultor(CrearAgricultorDto registro) {
    	
    	// Verificar si el alias ya existe
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorAlias(registro.getAlias());
        if (usuarioExistente.isPresent()) {
            log.warn("Alias '{}' ya está registrado", registro.getAlias());
            throw new IllegalArgumentException("El alias ya existe. Por favor, elige otro.");
        }
        
        Persona persona = new Persona();
        persona.setNombre(registro.getNombre());
        persona.setApellido(registro.getApellido());
        persona.setCorreo(registro.getCorreo());
        persona.setCedula(registro.getCedula());
        persona.setCelular(registro.getCelular());
        Persona personaGuardado = personaService.crearPersona(persona);
        
        Usuario usuario = new Usuario();
        usuario.setAlias(registro.getAlias());
        log.info("Creando usuario: {}", usuario.getAlias());
        usuario.setClave(registro.getClave());
        log.info("Creando clave: {}" ,registro.getClave());
        usuario.setPersona(personaGuardado);
        usuario.setActivo(true);

        Optional<Rol> rolAgricultor = rolRepository.findByNombre("AGRICULTOR");
        if (!rolAgricultor.isPresent()) {
        	throw new RuntimeException("No se encontró el rol 'AGRICULTOR'");
        }
        usuario.setRol(rolAgricultor.get());
        usuarioService.crearUsuario(usuario);
        log.info("Usuario clave: {} ", usuario.getClave());

        Agricultor agricultor = new Agricultor();
        agricultor.setPersona(personaGuardado);
        log.info("Agricultor registrado con ID: {}", agricultor.getId());

        return agricultorRepository.save(agricultor);
    }

    @Override
    public Agricultor obtenerPorId(Integer id) {
        // TODO Auto-generated method stub
        return agricultorRepository.findById(id).orElse(null);
    }

	@Override
	public Optional<Agricultor> findByPersonaId(Integer personaId) {
		// TODO Auto-generated method stub
		return agricultorRepository.findByPersonaId(personaId);
	}

	@Override
	public Agricultor actualizarAgricultor(Integer id, CrearAgricultorDto agricultorDto) {
		
		Agricultor agricultorExistente = agricultorRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Agricultor no encontrado con ID: " + id));

		    Persona persona = agricultorExistente.getPersona();

		    // Validar campos no nulos
		    if (agricultorDto.getNombre() == null || agricultorDto.getApellido() == null ||
		    	agricultorDto.getCedula() == null || agricultorDto.getCorreo() == null ||
		    	agricultorDto.getCelular() == null) {
		        throw new IllegalArgumentException("Todos los campos de persona son obligatorios");
		    }

		    // Actualizar campos de Persona
		    persona.setNombre(agricultorDto.getNombre());
		    persona.setApellido(agricultorDto.getApellido());
		    persona.setCedula(agricultorDto.getCedula());
		    persona.setCorreo(agricultorDto.getCorreo());
		    persona.setCelular(agricultorDto.getCelular());

		    personaService.actualizarPersona(persona.getId(), persona);

		   
		    return agricultorRepository.save(agricultorExistente);
	}

	


}
