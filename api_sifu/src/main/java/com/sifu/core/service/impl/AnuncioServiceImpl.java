package com.sifu.core.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifu.core.repo.AnuncioRepository;
import com.sifu.core.service.AnuncioService;
import com.sifu.core.utils.entity.Anuncio;

@Service
public class AnuncioServiceImpl implements AnuncioService {

	private static final Logger log = LogManager.getLogger(AnuncioServiceImpl.class);

	@Autowired
	private AnuncioRepository anuncioRepository;

	@Override
	public List<Anuncio> obtenerTodas() {
		log.info("obtenerTodas() called");
		// TODO Auto-generated method stub
		return anuncioRepository.findAll();
	}

	@Override
	public Anuncio crearAnuncio(Anuncio anuncio) {
		log.info("crearAnuncio() called");
		// TODO Auto-generated method stub
		return anuncioRepository.save(anuncio);
	}

	@Override
	public Anuncio obtenerPorId(Integer id) {
		log.info("obtenerPorId() called");
		// TODO Auto-generated method stub
		return anuncioRepository.findById(id).orElse(null);
	}

	@Override
	public Anuncio actualizarAnuncio(Integer id, Anuncio anuncio) {
		log.info("actualizarAnuncio() called");
		// TODO Auto-generated method stub
		Anuncio anuncioExistente = anuncioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
		anuncioExistente.setDescripcion(anuncio.getDescripcion());
		anuncioExistente.setEstado(anuncio.getEstado());
		return anuncioRepository.save(anuncioExistente);
	}

	@Override
	public void eliminarAnuncio(Integer id) {
		log.info("eliminarAnuncio() called");
		anuncioRepository.deleteById(id);

	}

	@Override
	public List<Anuncio> obtenerAnunciosPorAgricultor(Integer id) {
		log.info("obtenerAnunciosPorAgricultor() called");
		return anuncioRepository.findByAgricultorId(id);
	}

}
