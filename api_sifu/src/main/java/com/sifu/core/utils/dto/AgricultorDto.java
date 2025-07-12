package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.Agricultor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.sifu.core.utils.entity.Agricultor}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgricultorDto implements Serializable {
    private Integer id;
    private PersonaDto persona;

    public AgricultorDto(Agricultor entity) {
        id = entity.getId();
        persona = new PersonaDto(entity.getPersona());
    }

    public Agricultor toEntity() {
        Agricultor agricultor = new Agricultor();
        agricultor.setId(id);
        agricultor.setPersona(persona.toEntity());
        return agricultor;
    }
}