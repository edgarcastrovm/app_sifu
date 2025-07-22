package com.sifu.core.service;

import java.util.List;
import java.util.Optional;

import com.sifu.core.utils.entity.Producto;

public interface ProductoService {

	Producto crearProducto(Producto producto);
	Producto obtenerPorId(Integer id);
	Producto actualizarProducto(Integer id, Producto producto);
	Optional<Producto> findByNombre(String nombre);
	List <Producto> listarTodoProductos();
}
