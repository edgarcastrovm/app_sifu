package com.sifu.core.service;

import java.util.Optional;

import com.sifu.core.utils.entity.Usuario;

public interface UsuarioService {

	Usuario crearUsuario(Usuario usuario);
	Usuario obtenerPorId(Integer id);
	Optional<Usuario> buscarPorAlias(String alias);
	Usuario actualizarClave(Integer id, Usuario usuario);
}
