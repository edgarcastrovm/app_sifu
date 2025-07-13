package com.sifu.core.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.UsuarioRepository;
import com.sifu.core.service.UsuarioService;
import com.sifu.core.utils.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Usuario crearUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		String claveEncriptada = passwordEncoder.encode(usuario.getClave());
	    usuario.setClave(claveEncriptada);
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public Optional<Usuario> buscarPorAlias(String alias) {
		return usuarioRepository.findByAlias(alias);
	}

	@Override
	public Usuario actualizarClave(Integer id, Usuario usuario) {
		// TODO Auto-generated method stub
		
		Usuario usuarioExistente = usuarioRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id)); 

		String claveEncriptada = passwordEncoder.encode(usuario.getClave());
		usuarioExistente.setClave(claveEncriptada);
		 
	    return usuarioRepository.save(usuarioExistente);
	}
	
	
}
