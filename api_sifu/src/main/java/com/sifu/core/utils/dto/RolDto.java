package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.Rol}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolDto implements Serializable {
    private Integer id;
    private String nombre;

    public RolDto(Rol entiry) {
        this.id = entiry.getId();
        this.nombre = entiry.getNombre();
    }

    public Rol toEntity() {
        Rol entity = new Rol();
        entity.setId(this.id);
        entity.setNombre(this.nombre);
        return entity;
    }
}