package com.sifu.core.utils.dto.dominio;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabeceraFacturaDto {
    Integer numero;
    Integer idCliente;
    String nombre;
    String apellido;
    String dni;
    LocalDateTime fecha;
    BigDecimal valor;
}
