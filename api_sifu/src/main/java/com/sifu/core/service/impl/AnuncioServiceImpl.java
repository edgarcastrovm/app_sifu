package com.sifu.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.AnuncioRepository;
import com.sifu.core.service.AnuncioService;
import com.sifu.core.utils.entity.Anuncio;

@Service
public class AnuncioServiceImpl implements AnuncioService{
	
	@Autowired
	private AnuncioRepository anuncioRepository;

	@Override
	public List<Anuncio> obtenerTodas() {
		// TODO Auto-generated method stub
		return anuncioRepository.findAll();
	}

	@Override
	public Anuncio crearAnuncio(Anuncio anuncio) {
		// TODO Auto-generated method stub
		return anuncioRepository.save(anuncio);
	}

	@Override
	public Anuncio obtenerPorId(Integer id) {
		// TODO Auto-generated method stub
		return anuncioRepository.findById(id).orElse(null);
	}

	@Override
	public Anuncio actualizarAnuncio(Integer id, Anuncio anuncio) {
		// TODO Auto-generated method stub
		Anuncio anuncioExistente = anuncioRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));    
		anuncioExistente.setDescripcion(anuncio.getDescripcion());
		anuncioExistente.setEstado(anuncio.getEstado()); 
	    return anuncioRepository.save(anuncioExistente);
	}

	@Override
	public void eliminarAnuncio(Integer id) {
		anuncioRepository.deleteById(id);
		
	}

	@Override
	public List<Anuncio> obtenerAnunciosPorAgricultor(Integer id) {
		// TODO Auto-generated method stub
		
		return anuncioRepository.findByAgricultorId(id);
	}

	
	
	
	

}
