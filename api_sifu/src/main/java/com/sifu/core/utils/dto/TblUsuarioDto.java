package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.TblPersona;
import com.sifu.core.utils.entity.TblUsuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.TblUsuario}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblUsuarioDto implements Serializable {
    private Integer id;
    @NotNull
    private TblPersonaDto perRef;
    @NotNull
    @Size(max = 50)
    private String usuNombreUsuario;
    @NotNull
    @Size(max = 255)
    private String usuPasswordHash;
    @Size(max = 50)
    private String usuRol;
    private OffsetDateTime usuFechaUltimoLogin;
    @Size(max = 20)
    private String usuEstado;
    private OffsetDateTime usuFechaCreacion;

    public TblUsuarioDto(TblUsuario entity) {
        if(entity.getPerRef() != null) {
            this.perRef = new TblPersonaDto(entity.getPerRef());
        }
        this.id = entity.getId();
        this.usuNombreUsuario = entity.getUsuNombreUsuario();
        this.usuPasswordHash = entity.getUsuPasswordHash();
        this.usuRol = entity.getUsuRol();
        this.usuFechaUltimoLogin= entity.getUsuFechaUltimoLogin();
        this.usuEstado = entity.getUsuEstado();
        this.usuFechaCreacion = entity.getUsuFechaCreacion();
    }

    public TblUsuario toEntity() {
        TblUsuario entity = new TblUsuario();
        entity.setId(this.id);
        entity.setUsuNombreUsuario(this.usuNombreUsuario);
        entity.setUsuPasswordHash(this.usuPasswordHash);
        entity.setUsuRol(this.usuRol);
        usuFechaUltimoLogin = this.usuFechaUltimoLogin;
        usuEstado = this.usuEstado;
        usuFechaCreacion = this.usuFechaCreacion;
        return entity;
    }
}