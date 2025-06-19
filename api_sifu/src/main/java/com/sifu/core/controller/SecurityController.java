package com.sifu.core.controller;


import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.service.dominio.SecurityService;
import com.sifu.core.utils.dto.dominio.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> validateLogin(@RequestBody LoginDto loginDto) {
        ApiResponse<?> response;
        try {
            response = securityService.validateLogin(loginDto);
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error show all products", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error en login");
            return ResponseEntity.status(response.getCode()).body(response);
        }

    }
}
