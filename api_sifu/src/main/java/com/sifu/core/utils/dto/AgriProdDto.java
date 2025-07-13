package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.AgriProd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.AgriProd}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgriProdDto implements Serializable {
    private Integer id;
    private Integer stock;
    private AgricultorDto agricultor;
    private ProductoDto producto;

    public AgriProdDto(AgriProd entity) {
        id = entity.getId();
        if (entity.getStock() != null) {
            stock = entity.getStock().getCantidad();
        }else {
            stock = 0;
        }
        agricultor = new AgricultorDto(entity.getAgricultor());
        producto = new ProductoDto(entity.getProducto());
    }

    public AgriProd toEntity() {
        AgriProd agriProd = new AgriProd();
        agriProd.setId(id);
        agriProd.setAgricultor(agricultor.toEntity());
        return agriProd;
    }
}