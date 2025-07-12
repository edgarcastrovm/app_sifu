package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.Producto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoDto implements Serializable {
    private Integer id;
    private Double precio;
    private String nombre;
    private CategoriaProdDto categoriaProd;

    public ProductoDto(Producto producto) {
        this.id = producto.getId();
        this.precio = producto.getPrecio();
        this.nombre = producto.getNombre();
        if (producto.getCategoriaProd() != null) {
            this.categoriaProd = new CategoriaProdDto(producto.getCategoriaProd());
        }
    }
}