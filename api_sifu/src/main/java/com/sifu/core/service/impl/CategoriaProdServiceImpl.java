package com.sifu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.CategoriaProdRepository;
import com.sifu.core.service.CategoriaProdService;
import com.sifu.core.utils.entity.CategoriaProd;

@Service
public class CategoriaProdServiceImpl implements CategoriaProdService{

	@Autowired
	private CategoriaProdRepository categoriaProdRepository;

	@Override
	public CategoriaProd crearCategoriaProd(CategoriaProd categoriaProd) {
		// TODO Auto-generated method stub
		return categoriaProdRepository.save(categoriaProd);
	}

	@Override
	public CategoriaProd obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return categoriaProdRepository.findById(id).orElse(null);
	}

	@Override
	public CategoriaProd actualizarCategoriaProd(Integer id, CategoriaProd categoriaProd) {
		// TODO Auto-generated method stub
		CategoriaProd catExistente = categoriaProdRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Persona no encontrado con ID: " + id));
		
		catExistente.setNombre(categoriaProd.getNombre());
		return categoriaProdRepository.save(categoriaProd);
	}

	@Override
	public Optional<CategoriaProd> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return categoriaProdRepository.findByNombre(nombre);
	}

	@Override
	public List<CategoriaProd> listarTodasCategorias() {
		// TODO Auto-generated method stub
		return categoriaProdRepository.findAll();
	}

	
}
