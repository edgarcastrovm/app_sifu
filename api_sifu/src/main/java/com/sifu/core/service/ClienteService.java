package com.sifu.core.service;

import java.util.List;

import com.sifu.core.utils.dto.dominio.CrearClienteDto;
import com.sifu.core.utils.entity.Cliente;


public interface ClienteService {
	
	List<Cliente> obtenerTodas();
	Cliente crearCliente(CrearClienteDto cliente);
	Cliente obtenerPorId(Integer id);

}
