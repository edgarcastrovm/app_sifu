package com.sifu.core.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sifu.core.utils.entity.Stock}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDto implements Serializable {
    private Integer id;
    private Integer cantidad;
    private String uniMedida;
    private LocalDateTime fechaCreacion;
    private AgriProdDto producto;
}