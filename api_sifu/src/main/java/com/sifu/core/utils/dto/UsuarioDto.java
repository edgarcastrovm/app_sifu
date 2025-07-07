package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.Usuario}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto implements Serializable {
    private Integer id;
    private String alias;
    private String clave;
    private PersonaDto persona;
    private RolDto rol;

    public UsuarioDto(Usuario entity) {
        this.id = entity.getId();
        this.alias = entity.getAlias();
        this.clave = entity.getClave();
        this.persona = new PersonaDto(entity.getPersona());
        this.rol = new RolDto(entity.getRol());
    }

    public Usuario toEntity() {
        Usuario entity = new Usuario();
        entity.setAlias(this.alias);
        entity.setClave(this.clave);
        entity.setRol(this.rol.toEntity());
        return entity;
    }
}