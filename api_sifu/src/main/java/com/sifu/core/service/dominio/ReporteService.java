package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.controller.api.ReporteController;
import com.sifu.core.repo.dominio.ReporteProductoRepository;
import com.sifu.core.repo.dominio.ReporteRepository;
import com.sifu.core.utils.dto.dominio.ReporteFacturaDTO;
import com.sifu.core.utils.dto.dominio.ReporteProductosDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService implements IReporteService {
	private static final Logger log = LogManager.getLogger(ReporteService.class);
	private final ReporteRepository facturaRepository;
	
	@Autowired
	private ReporteProductoRepository reporteProductoRepository;

	public ReporteService(ReporteRepository facturaRepository) {
		this.facturaRepository = facturaRepository;
		this.reporteProductoRepository = reporteProductoRepository;
	}

	@Override
	public ApiResponse<?> ventas() {
		log.info("ventas() called");
		List<ReporteFacturaDTO> items = facturaRepository.obtenerReporteVentas();
		if (items != null && items.size() > 0) {
			return ApiResponse.success(items);
		} else {
			return ApiResponse.error(RC.NOT_FOUND);
		}
	}

	@Override
	public ApiResponse<?> productos() {
		log.info("productos() called");
		List<ReporteProductosDTO> items = reporteProductoRepository.obtenerReporteProductos();
		if (items != null && items.size() > 0) {
			return ApiResponse.success(items);
		} else {
			return ApiResponse.error(RC.NOT_FOUND);
		}
	}
}
