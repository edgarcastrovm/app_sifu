package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.TblPersona;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.TblPersona}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblPersonaDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 100)
    private String perNombres;
    @NotNull
    @Size(max = 100)
    private String perApellidos;
    @Size(max = 20)
    private String perIdentificacion;
    @Size(max = 20)
    private String perTelefono;
    @Size(max = 100)
    private String perEmail;
    @Size(max = 255)
    private String perDireccion;
    private LocalDate perFechaNacimiento;
    @Size(max = 10)
    private String perGenero;
    private OffsetDateTime perFechaRegistro;
    @Size(max = 20)
    private String perEstado;

    public TblPersonaDto(TblPersona entity) {
        this.id = entity.getId();
        this.perNombres = entity.getPerNombres();
        this.perApellidos = entity.getPerApellidos();
        this.perIdentificacion = entity.getPerIdentificacion();
        this.perTelefono = entity.getPerTelefono();
        this.perEmail = entity.getPerEmail();
        this.perDireccion = entity.getPerDireccion();
        this.perFechaNacimiento = entity.getPerFechaNacimiento();
        this.perGenero = entity.getPerGenero();
        this.perEstado = entity.getPerEstado();
    }

    public TblPersona toEntity() {
        TblPersona entity = new TblPersona();
        entity.setId(this.id);
        entity.setPerNombres(this.perNombres);
        entity.setPerApellidos(this.perApellidos);
        entity.setPerIdentificacion(this.perIdentificacion);
        entity.setPerTelefono(this.perTelefono);
        entity.setPerEmail(this.perEmail);
        entity.setPerDireccion(this.perDireccion);
        entity.setPerFechaNacimiento(this.perFechaNacimiento);
        entity.setPerGenero(this.perGenero);
        entity.setPerEstado(this.perEstado);
        return entity;
    }
}