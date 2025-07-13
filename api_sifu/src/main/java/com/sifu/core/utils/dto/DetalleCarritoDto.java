package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.DetalleCarrito;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.DetalleCarrito}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleCarritoDto implements Serializable {
    private Integer id;
    private Integer cantidad;
    private double subtotal;
    private CarritoDto carrito;
    private AgriProdDto agriProd;
    private ProductoDto producto;

    public DetalleCarritoDto(DetalleCarrito entity) {
        this.id = entity.getId();
        this.cantidad = entity.getCantidad();
        this.subtotal = entity.getSubtotal();
        if (entity.getProducto() != null) {
            this.producto = new ProductoDto(entity.getProducto());
        }
        if (entity.getAgriProd() != null) {
            this.agriProd = new AgriProdDto(entity.getAgriProd());
        }
    }

    public DetalleCarrito toEntity() {
        DetalleCarrito entity = new DetalleCarrito();
        entity.setId(this.id);
        entity.setCantidad(this.cantidad);
        entity.setSubtotal(this.subtotal);
        return entity;
    }
}