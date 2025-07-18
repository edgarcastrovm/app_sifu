package com.sifu.core.controller.api;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.service.dominio.ShopService;
import com.sifu.core.service.security.CustomIUserDetails;
import com.sifu.core.utils.dto.ProductoDto;
import com.sifu.core.utils.entity.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private static final Logger log = LogManager.getLogger(ShopController.class);
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> listarCategorias() {
        log.info("listarCategorias() called");
        return ResponseEntity.ok().body(shopService.listarCategorias());
    }

    @GetMapping("/productos")
    public ResponseEntity<?> listarProductos() {
    	log.info("listarProductos() called");
        return ResponseEntity.ok().body(shopService.listarProductos());
    }

    @GetMapping("/productos/agricultor/{id}")
    public ResponseEntity<?> listarProductosAgricultor(@PathVariable Integer id) {
    	log.info("listarProductosAgricultor() called");
        return ResponseEntity.ok().body(shopService.listarProductosAgricultor(id));
    }

    @GetMapping("/agricultores")
    public ResponseEntity<?> listarAgricultores() {
    	log.info("listarAgricultores() called");
        return ResponseEntity.ok().body(shopService.listarAgricultores());
    }

    @GetMapping("/agricultores/productos")
    public ResponseEntity<?> listarProductosAgricultor(Authentication authentication) {
    	log.info("listarProductosAgricultor() called");
        CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        return ResponseEntity.ok().body(shopService.listarProductosAgricultorByUser(usuario));
    }

    @GetMapping("/agricultores/productos/{id}")
    public ResponseEntity<?> listarAgricultorByProductoId(@PathVariable Integer id) {
        log.info("listarProductosAgricultor() called");
        return ResponseEntity.ok().body(shopService.listarAgricultorByProductoId(id));
    }

    @PostMapping("/add-item-cart")
    public ResponseEntity<?> agregarItemCarrito(Authentication authentication, @RequestBody ProductoDto item) {
    	log.info("agreagrItemCarrito() called");
        if (authentication==null){
            log.error("No se puede agregar el carrito de producto necesita loguearse al sistema");
            return ResponseEntity.badRequest().body(ApiResponse.error(RC.FORBIDDEN,"Necesita estar logueado como cliente"));
        }
        CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        return ResponseEntity.ok().body(shopService.agregarProductoAlCarrito(usuario, item));
    }

    @GetMapping("/cliente/my-cart")
    public ResponseEntity<?> verCarritoCliente(Authentication authentication) {
        log.info("verCarritoCliente() called");
        if (authentication==null){
            log.error("No se puede agregar el carrito de producto necesita loguearse al sistema");
            return ResponseEntity.badRequest().body(ApiResponse.error(RC.FORBIDDEN,"Necesita estar logueado como cliente"));
        }
        CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        return ResponseEntity.ok().body(shopService.verCarrito(usuario));
    }

    @DeleteMapping("/cliente/my-cart/producto/{idItem}")
    public ResponseEntity<?> eliminarItemCarrito(Authentication authentication,@PathVariable Integer idItem) {
        log.info("eliminarItemCarrito() called");
        if (authentication==null){
            log.error("No se puede eliminar producto necesita loguearse al sistema");
            return ResponseEntity.badRequest().body(ApiResponse.error(RC.FORBIDDEN,"Necesita estar logueado como cliente"));
        }
        CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        return ResponseEntity.ok().body(shopService.eliminarItemCarrito(usuario,idItem));
    }
}
