package com.sifu.core.service.impl;

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
	
	
}
