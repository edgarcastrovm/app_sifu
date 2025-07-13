package com.sifu.core.service;

import java.util.List;

import com.sifu.core.utils.entity.Anuncio;



public interface AnuncioService {
	List<Anuncio> obtenerTodas();
	Anuncio crearAnuncio(Anuncio anuncio) ;
	Anuncio obtenerPorId(Integer id);
	Anuncio actualizarAnuncio(Integer id, Anuncio anuncio);
	void eliminarAnuncio(Integer id);
	
	List<Anuncio> obtenerAnunciosPorAgricultor(Integer id);
	

}
