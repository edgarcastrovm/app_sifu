package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.CategoriaProd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.CategoriaProd}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaProdDto implements Serializable {
    private Integer id;
    private String nombre;

    public CategoriaProdDto(CategoriaProd entiry) {
        this.id = entiry.getId();
        this.nombre = entiry.getNombre();
    }

    public CategoriaProd toEntity() {
        CategoriaProd entity = new CategoriaProd();
        entity.setId(this.id);
        entity.setNombre(this.nombre);
        return entity;
    }
}