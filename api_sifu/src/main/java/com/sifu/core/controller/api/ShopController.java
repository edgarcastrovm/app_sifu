package com.sifu.core.controller.api;

import com.sifu.core.service.dominio.ShopService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private static final Logger log = LogManager.getLogger(ShopController.class);
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }
    @GetMapping("/categorias")
    public ResponseEntity<?> listarCategorias(){
        return ResponseEntity.ok().body(shopService.listarCategorias());
    }

    @GetMapping("/productos")
    public ResponseEntity<?> listarProductos(){
        return ResponseEntity.ok().body(shopService.listarProductos());
    }

    @GetMapping("/productos/agricultor/{id}")
    public ResponseEntity<?> listarProductosAgricultor(@PathVariable Integer id ){
        return ResponseEntity.ok().body(shopService.listarProductosAgricultor(id));
    }

    @GetMapping("/agricultores")
    public ResponseEntity<?> listarAgricultores(){
        return ResponseEntity.ok().body(shopService.listarAgricultores());
    }
}
