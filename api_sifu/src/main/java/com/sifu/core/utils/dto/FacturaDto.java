package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.Factura}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacturaDto implements Serializable {
    private Integer id;
    private LocalDateTime fechaCreacion;
    private Double total;
    private String estado;
    private String metodoPago;
    private ClienteDto cliente;
}