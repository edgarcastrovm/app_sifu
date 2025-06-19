package com.sifu.core.controller;


import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.service.dominio.ShowRoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class ShowRoomController {

    private final Logger log = LogManager.getLogger(ShowRoomController.class);
    private final ShowRoomService showRoomService;


    public ShowRoomController(ShowRoomService showRoomService) {
        this.showRoomService = showRoomService;
    }


    @GetMapping("/showroom/products")
    public ResponseEntity<ApiResponse> showAllProducts() {
        ApiResponse<?> response;
        try {
            response = showRoomService.showAllProducts();
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error show all products", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al obtener los productos");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }

    @GetMapping("/showroom/products/catalog")
    public ResponseEntity<ApiResponse> showProductsByCatalog(@RequestParam(required = false, defaultValue = "all") String name) {
        ApiResponse<?> response;
        try {
            if (name.equals("all")) {
                response = showRoomService.showAllProducts();
            } else {
                response = showRoomService.showProductsByCatalog(name);
            }
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error show products by catalog", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al obtener los productos por categorias");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }

    @GetMapping("/showroom/productors")
    public ResponseEntity<ApiResponse> showAllProductors() {
        ApiResponse<?> response;
        try {
            response = showRoomService.showAllProductors();
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error show all productors", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al obtener los productores");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }
    @GetMapping("/showroom/products/productors/{id}")
    public ResponseEntity<ApiResponse> showAllProductsByProdeucer(@PathVariable Integer id) {
        ApiResponse<?> response;
        try {
            response = showRoomService.showProductsByProductorId(id);
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error show all productors", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al obtener los productores");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }
}
