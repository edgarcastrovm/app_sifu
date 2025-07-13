package com.sifu.core.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agricultor")
public class AgricultorController {

    @GetMapping("/agregar-productos")
    public String agregarProducto(){
        return "agricultor/agregar-productos";
    }
    @GetMapping("/ver-productos")
    public String listarProductos(){
        return "agricultor/ver-productos";
    }
}
