package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.TblCatalogo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.TblCatalogo}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblCatalogoDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 100)
    private String catNombre;
    private String catDescripcion;
    private Integer catPadre;
    @Size(max = 20)
    private String catEstado;

    public TblCatalogoDto(TblCatalogo entity) {
        id = entity.getId();
        catNombre = entity.getCatNombre();
        catDescripcion = entity.getCatDescripcion();
        catPadre =  entity.getCatPadre();
        catEstado = entity.getCatEstado();
    }

    public TblCatalogo toEntity() {
        TblCatalogo entity = new TblCatalogo();
        entity.setId(id);
        entity.setCatNombre(catNombre);
        entity.setCatDescripcion(catDescripcion);
        entity.setCatPadre(catPadre);
        entity.setCatEstado(catEstado);
        return entity;
    }
}