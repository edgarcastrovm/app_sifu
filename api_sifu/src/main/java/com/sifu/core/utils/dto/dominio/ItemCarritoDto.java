package com.sifu.core.utils.dto.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoDto {
    private Integer id;
    private Integer idAgricultor;
    private Integer idProducto;
    private Integer idCategoria;
    private Integer cantidad;
}
