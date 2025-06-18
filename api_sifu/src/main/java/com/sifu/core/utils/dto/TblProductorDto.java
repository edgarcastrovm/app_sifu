package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sifu.core.utils.entity.TblProductor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.TblProductor}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TblProductorDto implements Serializable {
    private Integer id;
    @NotNull
    private TblPersonaDto perRef;
    @NotNull
    @Size(max = 100)
    private String proNombreContacto;
    @NotNull
    @Size(max = 100)
    private String proMunicipio;
    @Size(max = 100)
    private String proParroquia;
    @NotNull
    @Size(max = 50)
    private String proTipoProduccion;
    private OffsetDateTime proFechaRegistro;

    public TblProductorDto(TblProductor entity) {
        if(entity.getPerRef() != null) {
             perRef = new TblPersonaDto(entity.getPerRef());
        }
        id = entity.getId();
        proNombreContacto = entity.getProNombreContacto();
        proMunicipio = entity.getProMunicipio();
        proParroquia = entity.getProParroquia();
        proTipoProduccion = entity.getProTipoProduccion();
        proFechaRegistro = entity.getProFechaRegistro();
    }

    public TblProductor toEntity() {
        TblProductor entity = new TblProductor();
        entity.setPerRef(perRef.toEntity());
        entity.setId(id);
        entity.setProNombreContacto(proNombreContacto);
        entity.setProMunicipio(proMunicipio);
        entity.setProParroquia(proParroquia);
        entity.setProTipoProduccion(proTipoProduccion);
        entity.setProFechaRegistro(proFechaRegistro);
        return entity;
    }

}