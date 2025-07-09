package com.sifu.core.controller.api;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.service.dominio.IReporteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {

    private static final Logger log= LogManager.getLogger(ReporteController.class);

    private final IReporteService reporteService;

    public ReporteController(IReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<?> ventas(){
        ApiResponse response = reporteService.ventas();
        return ResponseEntity.ok(response);
    }

}
