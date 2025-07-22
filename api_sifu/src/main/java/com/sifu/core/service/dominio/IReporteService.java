package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;

public interface IReporteService {
    ApiResponse<?> ventas();
    ApiResponse<?> productos();
    
}
