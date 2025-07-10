package com.sifu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.ClienteRepository;
import com.sifu.core.repo.RolRepository;
import com.sifu.core.service.ClienteService;
import com.sifu.core.service.PersonaService;
import com.sifu.core.service.UsuarioService;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.dto.dominio.CrearClienteDto;
import com.sifu.core.utils.entity.Cliente;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Rol;
import com.sifu.core.utils.entity.Usuario;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PersonaService personaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Cliente> obtenerTodas() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}

	@Override
	public Cliente crearCliente(CrearClienteDto registroC) {
		Persona personaC = new Persona();
    	personaC.setNombre(registroC.getNombre());
    	personaC.setApellido(registroC.getApellido());
    	personaC.setCorreo(registroC.getCorreo());
    	personaC.setCedula(registroC.getCedula());
    	personaC.setCelular(registroC.getCelular());
    	Persona personaGuardadoC = personaService.crearPersona(personaC);
		
    	Usuario usuarioC = new Usuario();
    	usuarioC.setAlias(registroC.getAlias());
    	usuarioC.setClave(passwordEncoder.encode(registroC.getClave())); 
    	usuarioC.setPersona(personaGuardadoC);
    	usuarioC.setActivo(true);
    	
    	Optional<Rol> rolCliente = rolRepository.findByNombre("CLIENTE");
        if(! rolCliente.isPresent()) {
        	return null;
        }
        usuarioC.setRol(rolCliente.get());
		usuarioService.crearUsuario(usuarioC);
		
		Cliente cliente = new Cliente();
		cliente.setEntidadSFL(registroC.getEntidadSFL() != null ? registroC.getEntidadSFL() : false);
		cliente.setPersona(personaGuardadoC);
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return clienteRepository.findById(id).orElse(null);
	}
}
