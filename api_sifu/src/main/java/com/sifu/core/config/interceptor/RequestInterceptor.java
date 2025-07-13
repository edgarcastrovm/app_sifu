package com.sifu.core.config.interceptor;

import com.sifu.core.config.security.JwtUtil;
import com.sifu.core.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final String REQUEST_ID_MDC_KEY = "requestId";

    private final Logger log = LogManager.getLogger(RequestInterceptor.class);
    private final JwtUtil jwtUtil;

    public RequestInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestUri = request.getRequestURI();
        if(requestUri.startsWith("/sifu/assets")) {
            return true;
        }

        // Generar un UUID como requestId
        String requestId = UUID.randomUUID().toString();

        // Agregar el requestId al MDC (Mapped Diagnostic Context) de Log4j2
        ThreadContext.put(REQUEST_ID_MDC_KEY, requestId);
        // Log de inicio de request
        log.info("Iniciando solicitud - Método: {}, URI: {}",
                request.getMethod(), request.getRequestURI());


        if (requestUri.startsWith(Constants.API_PROTECTED)) {
            // Obtener el token del header
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validarToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // Extrae el usuario
            String userId = jwtUtil.extraeruserId(token);

            // Agregar header personalizado
            if (response instanceof HttpServletResponse) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setHeader(Constants.USER_ID_HEADER, userId);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Limpiar el MDC después de la petición
        ThreadContext.remove(REQUEST_ID_MDC_KEY);
        ThreadContext.clearAll();
    }

}
