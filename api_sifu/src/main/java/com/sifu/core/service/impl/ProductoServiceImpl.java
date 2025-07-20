package com.sifu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.ProductoRepository;
import com.sifu.core.service.CategoriaProdService;
import com.sifu.core.service.ProductoService;
import com.sifu.core.utils.entity.CategoriaProd;
import com.sifu.core.utils.entity.Producto;

@Service
public class ProductoServiceImpl implements ProductoService{

private static final Logger log = LogManager.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaProdService categoriaProdService;

	@Override
	public Producto crearProducto(Producto producto) {
		// TODO Auto-generated method stub
		Optional<Producto> productoExistente = productoRepository.findByNombre(producto.getNombre());
		if (productoExistente.isPresent()) {
			log.warn("Producto '{}' ya está registrado", producto.getNombre());
			throw new IllegalArgumentException("El producto ya existe. ");
		}
		// Validar si la categoría existe correctamente
	    if (producto.getCategoriaProd() == null || producto.getCategoriaProd().getId() == null) {
	        throw new IllegalArgumentException("Debe seleccionar una categoría válida.");
	    }
	    
	    /*Optional<CategoriaProd> cat = categoriaProdService.findByNombre(producto.getCategoriaProd().getNombre());
		if (!cat.isPresent()) {
			throw new RuntimeException("No se encontró la categoria de producto ");
		}*/
	    CategoriaProd categoria = categoriaProdService.findByNombre(producto.getCategoriaProd().getNombre())
	    		.orElseThrow(() -> new RuntimeException("No se encontró la categoría de producto"));


        producto.setCategoriaProd(categoria);
        
		return productoRepository.save(producto);
	}

	@Override
	public Producto obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return productoRepository.findById(id).orElse(null);
	}

	@Override
	public Producto actualizarProducto(Integer id, Producto producto) {
		// TODO Auto-generated method stub
		Producto productoExistente = productoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id)); 

		
		productoExistente.setCategoriaProd(producto.getCategoriaProd());
		productoExistente.setPrecio(producto.getPrecio());
		productoExistente.setNombre(producto.getNombre());
		productoExistente.setImage(producto.getImage());
		
		return productoRepository.save(productoExistente);
	}

	@Override
	public Optional<Producto> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return productoRepository.findByNombre(nombre);
	}

	@Override
	public List<Producto> listarTodoProductos() {
		// TODO Auto-generated method stub
		return productoRepository.findAll();
	}
	
	
}
