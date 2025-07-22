package com.sifu.core.controller;

import com.sifu.core.service.google.GoogleCloudStorageService; // Cambiado para usar el nuevo servicio de GCS
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controlador REST para manejar la subida de imágenes a Google Cloud Storage.
 * Este controlador expone un endpoint para recibir imágenes codificadas en Base64.
 */
@RestController
public class ImageUploadController {

    private static final Logger log = LogManager.getLogger(ImageUploadController.class);

    // Inyección del nuevo servicio de Google Cloud Storage
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

    /**
     * Endpoint para subir una imagen codificada en Base64 a Google Cloud Storage.
     *
     * @param base64Image La cadena Base64 de la imagen.
     * @param fileName    El nombre deseado para el archivo en GCS.
     * @return ResponseEntity con un mensaje de éxito y la URL del archivo, o un mensaje de error.
     */
    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(
            @RequestParam("base64Image") String base64Image,
            @RequestParam("fileName") String fileName) {
        try {
            log.info("Solicitud de subida de imagen recibida para GCS: {}", fileName);
            // Llama al servicio de Google Cloud Storage para subir la imagen
            String fileUrl = googleCloudStorageService.uploadBase64Image(base64Image, fileName);
            log.info("Imagen subida correctamente a GCS. URL: {}", fileUrl);
            return ResponseEntity.ok("Imagen subida correctamente. URL: " + fileUrl);
        } catch (IllegalArgumentException e) {
            log.error("Error en el formato de la imagen Base64 o nombre de archivo: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error en el formato de la imagen Base64 o nombre de archivo: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error de I/O al subir la imagen a Google Cloud Storage: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen a Google Cloud Storage: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al subir la imagen a GCS: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al subir la imagen: " + e.getMessage());
        }
    }
}
