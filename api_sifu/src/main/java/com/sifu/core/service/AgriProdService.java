package com.sifu.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sifu.core.utils.entity.AgriProd;

public interface AgriProdService {

	
	AgriProd crearAgriProd(AgriProd agriProd) ;
	AgriProd actualizarAgriProd(Integer id, AgriProd agriProd);
	AgriProd obtenerPorId(Integer id);
	List<AgriProd> findByAgricultor_Id(Integer agricultorId);
	void eliminarAgriProd(Integer id);


	boolean existsByAgricultorIdAndProductoNombre(@Param("agricultorId") Integer agricultorId,
            @Param("nombreProducto") String nombreProducto);
	
	boolean existsByAgricultorIdAndProductoNombreExceptId(@Param("agricultorId") Integer agricultorId,
                    @Param("nombreProducto") String nombreProducto,
                    @Param("excludeId") Integer excludeId);
}
