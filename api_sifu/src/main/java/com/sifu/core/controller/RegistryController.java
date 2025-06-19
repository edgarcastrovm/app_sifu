package com.sifu.core.controller;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.service.dominio.RegistryService;
import com.sifu.core.utils.dto.TblProductoDto;
import com.sifu.core.utils.dto.TblProductorDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/registry")
public class RegistryController {
    private final Logger log = LogManager.getLogger(RegistryController.class);
    private final RegistryService registryService;


    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping("/product")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody TblProductoDto tblProductoDto) {
        ApiResponse<?> response;
        try {
            response = registryService.registryProduct(tblProductoDto);
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error al agregar al carrito", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al agregar al carrito");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }
}
