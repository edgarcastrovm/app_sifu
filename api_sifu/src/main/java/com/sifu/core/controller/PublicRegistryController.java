package com.sifu.core.controller;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import com.sifu.core.service.dominio.RegistryService;
import com.sifu.core.utils.dto.TblPersonaDto;
import com.sifu.core.utils.dto.TblProductorDto;
import com.sifu.core.utils.dto.TblUsuarioDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicRegistryController {

    private final Logger log = LogManager.getLogger(PublicRegistryController.class);
    private final RegistryService registryService;


    public PublicRegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping("/registry/customer")
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody TblPersonaDto tblPersonaDto) {
        ApiResponse<?> response;
        try {
            response = registryService.registryPerson(tblPersonaDto);
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error create customer", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al registrar el cliente");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }

    @PostMapping("/registry/user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody TblUsuarioDto tblUsuarioDto) {
        ApiResponse<?> response;
        try {
            response = registryService.registryUser(tblUsuarioDto);
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error create user", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al registrar el usuario");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }


    @PostMapping("/registry/producer")
    public ResponseEntity<ApiResponse> createProducer(@RequestBody TblProductorDto tblProductorDto) {
        ApiResponse<?> response;
        try {
            response = registryService.registryProducer(tblProductorDto);
            return ResponseEntity.status(response.getCode()).body(response);
        } catch (Exception e) {
            log.error("Error create producer", e.getMessage());
            response = ApiResponse.error(RC.BAD_REQUEST, "Error al registrar el productor");
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }

}
