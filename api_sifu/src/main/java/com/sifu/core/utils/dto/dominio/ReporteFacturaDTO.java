package com.sifu.core.utils.dto.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReporteFacturaDTO {
    Long getFacturaId();
    LocalDateTime getFechaVenta();
    String getMetodoPago();
    BigDecimal getTotalFactura();
    String getCliente();
    String getCedulaCliente();
    String getEmailCliente();
    Integer getCantidad();
    String getProducto();
    String getCategoria();
    BigDecimal getPrecioUnitario();
    BigDecimal getSubtotalItem();
    String getAgricultor();
    Integer getStockDisponible();
    String getUnidadMedida();
}
