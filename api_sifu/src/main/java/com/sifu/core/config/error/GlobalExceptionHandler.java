package com.sifu.core.config.error;

import com.sifu.core.config.http.ApiResponse;
import com.sifu.core.config.http.RC;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Para errores de @Valid en @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(RC.BAD_REQUEST, errors));
    }

    // Para errores de validación en @RequestParam o @PathVariable (ConstraintViolation)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
            errors.put(field, violation.getMessage());
        });

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(RC.BAD_REQUEST, errors));
    }

    // Para errores de NotFound
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity
                .status(RC.NOT_FOUND.code())
                .body(ApiResponse.error(RC.NOT_FOUND, "Not Found" + ex.getRequestURL()));
    }

    // Para Errores inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {

        ex.printStackTrace(); // O usa un logger
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.internalError("Ocurrió un error inesperado"));
    }


}
