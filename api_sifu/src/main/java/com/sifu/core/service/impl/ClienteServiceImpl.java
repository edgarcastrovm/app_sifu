package com.sifu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.ClienteRepository;
import com.sifu.core.repo.RolRepository;
import com.sifu.core.service.ClienteService;
import com.sifu.core.service.PersonaService;
import com.sifu.core.service.UsuarioService;
import com.sifu.core.utils.dto.dominio.ActualizarClienteDto;
import com.sifu.core.utils.dto.dominio.CrearClienteDto;
import com.sifu.core.utils.entity.Cliente;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Rol;
import com.sifu.core.utils.entity.Usuario;

@Service
public class ClienteServiceImpl implements ClienteService{

	private static final Logger log = LogManager.getLogger(AgricultorServiceImpl.class);
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PersonaService personaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolRepository rolRepository;
	
	@Override
	public List<Cliente> obtenerTodas() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}

	@Override
	public Cliente crearCliente(CrearClienteDto registroC) {
		
		// Verificar si el alias ya existe
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorAlias(registroC.getAlias());
        if (usuarioExistente.isPresent()) {
            log.warn("Alias '{}' ya está registrado", registroC.getAlias());
            throw new IllegalArgumentException("El alias ya existe. Por favor, elige otro alias.");
        }
		
		Persona personaC = new Persona();
    	personaC.setNombre(registroC.getNombre());
    	personaC.setApellido(registroC.getApellido());
    	personaC.setCorreo(registroC.getCorreo());
    	personaC.setCedula(registroC.getCedula());
    	personaC.setCelular(registroC.getCelular());
    	Persona personaGuardadoC = personaService.crearPersona(personaC);
		
    	Usuario usuarioC = new Usuario();
    	usuarioC.setAlias(registroC.getAlias());
    	log.info("Creando usuario: {}", usuarioC.getAlias());
    	usuarioC.setClave(registroC.getClave()); 
        log.info("Creando clave: {}" ,registroC.getClave());
    	usuarioC.setPersona(personaGuardadoC);
    	usuarioC.setActivo(true);
    	
    	Optional<Rol> rolCliente = rolRepository.findByNombre("CLIENTE");
        if(! rolCliente.isPresent()) {
        	throw new RuntimeException("No se encontró el rol 'CLIENTE'");
        }
        usuarioC.setRol(rolCliente.get());
		usuarioService.crearUsuario(usuarioC);
		
		Cliente cliente = new Cliente();
		cliente.setEntidadSFL(registroC.getEntidadSFL() != null ? registroC.getEntidadSFL() : false);
		cliente.setPersona(personaGuardadoC);
		log.info("Cliente registrado con ID: {}", cliente.getId());
		
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	public Optional<Cliente> findByPersonaId(Integer personaId) {
		// TODO Auto-generated method stub
		return clienteRepository.findByPersonaId(personaId);
	}

	@Override
	public Cliente actualizarCliente(Integer id, ActualizarClienteDto clienteDto) {
		// TODO Auto-generated method stub
		Cliente clienteExistente = clienteRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

		    Persona persona = clienteExistente.getPersona();

		    // Validar campos no nulos
		    if (clienteDto.getNombre() == null || clienteDto.getApellido() == null ||
		        clienteDto.getCedula() == null || clienteDto.getCorreo() == null ||
		        clienteDto.getCelular() == null) {
		        throw new IllegalArgumentException("Todos los campos de persona son obligatorios");
		    }

		    // Actualizar campos de Persona
		    persona.setNombre(clienteDto.getNombre());
		    persona.setApellido(clienteDto.getApellido());
		    persona.setCedula(clienteDto.getCedula());
		    persona.setCorreo(clienteDto.getCorreo());
		    persona.setCelular(clienteDto.getCelular());

		    personaService.actualizarPersona(persona.getId(), persona);

		    clienteExistente.setEntidadSFL(clienteDto.getEntidadSFL() != null ? clienteDto.getEntidadSFL() : false);

		    return clienteRepository.save(clienteExistente);
		}
}
