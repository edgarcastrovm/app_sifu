package com.sifu.core.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase de configuración para inicializar el cliente de Google Cloud Storage.
 * Utiliza un archivo de credenciales de cuenta de servicio para la autenticación.
 */
@Configuration
public class GoogleCloudStorageConfig {

    private static final Logger log = LogManager.getLogger(GoogleCloudStorageConfig.class);

    // Ruta al archivo de credenciales de la cuenta de servicio, inyectada desde application.properties
    @Value("${google.cloud.credentials.file-path}")
    private String credentialsFile;

    /**
     * Define un bean para el cliente de Google Cloud Storage.
     * Este bean será inyectado en otros componentes que necesiten interactuar con GCS.
     *
     * @return Una instancia del cliente Storage de Google Cloud.
     * @throws IOException Si ocurre un error al leer el archivo de credenciales.
     */
    @Bean
    public Storage googleCloudStorage() throws IOException {
        log.info("Iniciando configuración de Google Cloud Storage...");
        log.info("Ruta del archivo de credenciales: {}", credentialsFile);

        // Resuelve la ruta absoluta del archivo de credenciales
        Path path = Paths.get(credentialsFile);
        log.info("Ruta absoluta del archivo de credenciales: {}", path.toAbsolutePath());

        // Verifica si el archivo existe y es legible
        if (!Files.exists(path)) {
            log.error("El archivo de credenciales NO EXISTE en: {}", path.toAbsolutePath());
            throw new IOException("Archivo de credenciales no encontrado en la ruta especificada: " + path.toAbsolutePath());
        }
        if (!Files.isReadable(path)) {
            log.error("El archivo de credenciales NO TIENE PERMISOS DE LECTURA en: {}", path.toAbsolutePath());
            throw new IOException("Permisos de lectura insuficientes para el archivo de credenciales: " + path.toAbsolutePath());
        }

        // Abre un InputStream para leer el archivo de credenciales
        try (InputStream in = new FileInputStream(path.toFile())) {
            // Carga las credenciales de la cuenta de servicio desde el InputStream
            GoogleCredentials credentials = GoogleCredentials.fromStream(in);
            log.info("Credenciales de Google Cloud cargadas exitosamente.");

            // Construye y devuelve la instancia del cliente Storage
            return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (Exception e) {
            log.error("Error al inicializar el cliente de Google Cloud Storage: {}", e.getMessage(), e);
            throw new IOException("Error al inicializar Google Cloud Storage: " + e.getMessage(), e);
        }
    }
}
