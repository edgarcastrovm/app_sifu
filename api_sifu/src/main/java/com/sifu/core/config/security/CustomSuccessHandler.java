package com.sifu.core.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger log = LogManager.getLogger(CustomSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.info("onAuthenticationSuccess authorities : {}", authorities);
        String contextPath = request.getContextPath();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            log.info("ROLE:{}",role);
            log.info("contextPath:{}",contextPath);

            if (role.equals("ROL_ADMIN")) {
                response.sendRedirect(contextPath + "/admin/panel");
                return;
            } else if (role.equals("ROLE_CLIENTE")) {
                response.sendRedirect(contextPath + "/cliente/perfil");
                return;
            } else if (role.equals("ROLE_AGRICULTOR")) {
                response.sendRedirect(contextPath + "/agricultor/dashboard");
                return;
            }
        }
        response.sendRedirect(contextPath + "/site");
    }
}
