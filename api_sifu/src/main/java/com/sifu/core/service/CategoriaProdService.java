package com.sifu.core.service;

import java.util.List;
import java.util.Optional;

import com.sifu.core.utils.entity.CategoriaProd;

public interface CategoriaProdService {

	CategoriaProd crearCategoriaProd(CategoriaProd categoriaProd);
	CategoriaProd obtenerPorId(Integer id);
	CategoriaProd actualizarCategoriaProd(Integer id, CategoriaProd categoriaProd);
	Optional<CategoriaProd> findByNombre(String nombre);
	List <CategoriaProd> listarTodasCategorias();
}
