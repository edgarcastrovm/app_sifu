package com.sifu.core.repo.dominio;

import com.sifu.core.constants.QueryProductos;

import com.sifu.core.utils.dto.dominio.ReporteProductosDTO;

import com.sifu.core.utils.entity.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteProductoRepository extends JpaRepository<Producto, Long> {

	 @Query(value = QueryProductos.SQL_PRODUCTOS, nativeQuery = true)
	 List<ReporteProductosDTO> obtenerReporteProductos();

}
