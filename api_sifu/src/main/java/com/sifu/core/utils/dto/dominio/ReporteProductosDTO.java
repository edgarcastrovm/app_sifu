package com.sifu.core.utils.dto.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReporteProductosDTO {

	Integer getCantidad();

	String getProducto();

	String getCategoria();

	BigDecimal getPrecioUnitario();

}
