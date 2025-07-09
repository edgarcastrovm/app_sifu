package com.sifu.core.repo.dominio;

import com.sifu.core.constants.QueryConstants;
import com.sifu.core.utils.dto.dominio.ReporteFacturaDTO;
import com.sifu.core.utils.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Factura, Long> {

    @Query(value = QueryConstants.SQL_VENTAS, nativeQuery = true)
    List<ReporteFacturaDTO> obtenerReporteVentas();
}
