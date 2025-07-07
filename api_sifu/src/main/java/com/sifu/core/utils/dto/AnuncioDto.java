package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.Anuncio}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnuncioDto implements Serializable {
    private Integer id;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private AgricultorDto agricultor;
}