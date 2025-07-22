package com.sifu.core.controller.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sifu.core.controller.api.ReporteController;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger log= LogManager.getLogger(AdminController.class);

    @GetMapping("/")
    public String index() {
    	log.info("index() called");
        return "/admin/panel";
    }

    @GetMapping("/panel")
    public String panel() {
    	log.info("panel() called");
        return "/admin/panel";
    }

    @GetMapping("/reportes-ia")
    public String reportesIa() {
    	log.info("reportesIa() called");
        return "/admin/reportes/reportes_ia";
    }

    @GetMapping("/reportes")
    public String reportes() {
    	log.info("resportes() called");
        return "/admin/reportes/reportes";
    }
    
    @GetMapping("/reportes-productos")
    public String reporteProductos() {
    	log.info("resportes() called");
        return "/admin/reportes/reporte_productos";
    }
}
