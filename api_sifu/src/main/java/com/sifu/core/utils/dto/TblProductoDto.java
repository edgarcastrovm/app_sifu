package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.TblCatalogo;
import com.sifu.core.utils.entity.TblProducto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.TblProducto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblProductoDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 100)
    private String prdNombre;
    private String prdDescripcion;
    @NotNull
    @Size(max = 20)
    private String prdUnidadMedida;
    @NotNull
    private BigDecimal prdPrecio;
    @NotNull
    private BigDecimal prdCantidadDisponible;
    @Size(max = 255)
    private String prdUrlFoto;
    @Size(max = 20)
    private String prdEstado;
    @NotNull
    private TblProductorDto proRef;
    @NotNull
    private TblCatalogoDto catRef;
    private OffsetDateTime prdFechaCreacion;
    private OffsetDateTime prdUltimaActualizacion;

    public TblProductoDto(TblProducto entity) {
        id = entity.getId();
        prdNombre = entity.getPrdNombre();
        prdDescripcion = entity.getPrdDescripcion();
        catRef = new TblCatalogoDto(entity.getCatRef());
        prdFechaCreacion = entity.getPrdFechaCreacion();

    }

    public TblProducto toEntity() {
        TblProducto entity = new TblProducto();
        entity.setId(id);
        entity.setPrdNombre(prdNombre);
        entity.setPrdDescripcion(prdDescripcion);
        entity.setCatRef(catRef.toEntity());
        entity.setPrdFechaCreacion(prdFechaCreacion);
        return entity;
    }
}