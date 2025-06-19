package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.TblOfertaProducto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.TblOfertaProducto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblOfertaProductoDto implements Serializable {
    private Integer id;
    @NotNull
    private TblProductorDto proRef;
    @NotNull
    private TblProductoDto prdRef;
    @NotNull
    @Size(max = 20)
    private String ofeUnidadMedida;
    @NotNull
    private BigDecimal ofePrecio;
    @NotNull
    private BigDecimal ofeCantidadDisponible;
    @Size(max = 255)
    private String ofeUrlFoto;
    @Size(max = 20)
    private String ofeEstado;
    private OffsetDateTime ofeFechaCreacion;
    private OffsetDateTime ofeUltimaActualizacion;

    public TblOfertaProductoDto(TblOfertaProducto entity) {
        if (entity.getProRef() != null) {
            this.proRef = new TblProductorDto(entity.getProRef());
        }
        if (entity.getPrdRef() != null) {
            this.prdRef = new TblProductoDto(entity.getPrdRef());
        }
        this.id = entity.getId();
        this.ofeUnidadMedida = entity.getOfeUnidadMedida();
        this.ofePrecio = entity.getOfePrecio();
        this.ofeCantidadDisponible = entity.getOfeCantidadDisponible();
        this.ofeUrlFoto = entity.getOfeUrlFoto();
        this.ofeEstado = entity.getOfeEstado();
        this.ofeFechaCreacion = entity.getOfeFechaCreacion();
        this.ofeUltimaActualizacion = entity.getOfeUltimaActualizacion();
    }

    public TblOfertaProducto toEntity() {
        TblOfertaProducto entity = new TblOfertaProducto();
        entity.setId(id);
        if (proRef != null) {
            entity.setProRef(proRef.toEntity());
        }
        if (prdRef != null) {
            entity.setPrdRef(prdRef.toEntity());
        }
        entity.setOfeUnidadMedida(ofeUnidadMedida);
        entity.setOfePrecio(ofePrecio);
        entity.setOfeCantidadDisponible(ofeCantidadDisponible);
        entity.setOfeUrlFoto(ofeUrlFoto);
        entity.setOfeEstado(ofeEstado);
        entity.setOfeFechaCreacion(ofeFechaCreacion);
        entity.setOfeUltimaActualizacion(ofeUltimaActualizacion);
        return entity;
    }
}