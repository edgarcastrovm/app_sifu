package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.Persona;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.Persona}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaDto implements Serializable {
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer cedula;
    private String correo;
    private Integer celular;

    public PersonaDto(Persona entiry) {
        this.nombre = entiry.getNombre();
        this.apellido = entiry.getApellido();
        this.cedula = entiry.getCedula();
        this.correo = entiry.getCorreo();
        this.celular = entiry.getCelular();
    }

    public Persona  toEntity() {
        Persona persona = new Persona();
        persona.setId(this.id);
        persona.setNombre(this.nombre);
        persona.setApellido(this.apellido);
        persona.setCedula(this.cedula);
        persona.setCorreo(this.correo);
        persona.setCelular(this.celular);
        return persona;
    }
}