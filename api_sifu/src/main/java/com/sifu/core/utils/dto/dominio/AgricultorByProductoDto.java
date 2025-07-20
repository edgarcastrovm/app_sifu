package com.sifu.core.utils.dto.dominio;

import com.sifu.core.utils.entity.AgriProd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgricultorByProductoDto {
    private Integer idAgricultor;
    private String nombreAgricultor;
    private String correoAgricultor;
    private String telefonoAgricultor;
    private String provincia;
    private String canton;
    private Integer idProducto;
    private String nomnreProducto;
    private Integer idCategoria;
    private String nombreCategoria;
    private Integer stock;

    public AgricultorByProductoDto(AgriProd entity) {
        this.idAgricultor = entity.getAgricultor().getId();
        this.nombreAgricultor = entity.getAgricultor().getPersona().getNombre()
                + " " + entity.getAgricultor().getPersona().getApellido();
        this.correoAgricultor = entity.getAgricultor().getPersona().getCorreo();
        this.telefonoAgricultor = entity.getAgricultor().getPersona().getCelular();
        this.provincia = entity.getAgricultor().getPersona().getProvincia();
        this.canton = entity.getAgricultor().getPersona().getCanton();
        this.idProducto = entity.getProducto().getId();
        this.nomnreProducto = entity.getProducto().getNombre();
        this.idCategoria = entity.getProducto().getCategoriaProd().getId();
        this.nombreCategoria = entity.getProducto().getCategoriaProd().getNombre();
        if (entity.getStock()!=null) {
            this.stock = entity.getStock().getCantidad();
        }else {
            this.stock = 0;
        }

    }
}
