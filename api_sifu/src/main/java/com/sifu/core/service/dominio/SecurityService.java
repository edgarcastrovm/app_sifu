package com.sifu.core.service.dominio;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.config.security.JwtUtil;
import com.sifu.core.controller.ShowRoomController;
import com.sifu.core.repo.TblUsuarioRepository;
import com.sifu.core.utils.dto.dominio.LoginDto;
import com.sifu.core.utils.entity.TblUsuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    private final Logger log = LogManager.getLogger(SecurityService.class);
    private final TblUsuarioRepository tblUsuarioRepository;
    private final JwtUtil jwtUtil;

    public SecurityService(TblUsuarioRepository tblUsuarioRepository, JwtUtil jwtUtil) {
        this.tblUsuarioRepository = tblUsuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    public ApiResponse<LoginDto> validateLogin(LoginDto loginDto) {
        try {
            Optional<TblUsuario> login = tblUsuarioRepository.findByUsuNombreUsuarioAndUsuPasswordHash(loginDto.getEmail(), loginDto.getPassword());
            if (login.isPresent()) {
                LoginDto loginSuccess = new LoginDto( login.get());
                String token = jwtUtil.generarToken(login.get().getId().toString());
                loginSuccess.setToken(token);
                loginSuccess.setPassword(null);

                log.info("Login success: {}", login.get().getUsuNombreUsuario());
                return ApiResponse.success(loginSuccess);
            } else {
                log.error("Login failed: {}", loginDto.getEmail());
                return ApiResponse.error(RC.BAD_REQUEST, "Usuario o Clave inválido");
            }
        } catch (Exception e) {
            log.error("Error registry user: {}", e.getMessage());
            return ApiResponse.error(RC.BAD_REQUEST, "No se puede validar el login");
        }

    }
}
