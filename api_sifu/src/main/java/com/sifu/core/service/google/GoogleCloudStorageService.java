package com.sifu.core.service.google;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

/**
 * Servicio para interactuar con Google Cloud Storage (GCS), específicamente para subir imágenes.
 */
@Service
public class GoogleCloudStorageService {

    private static final Logger log = LogManager.getLogger(GoogleCloudStorageService.class);

    // Inyección del cliente de Google Cloud Storage configurado en GoogleCloudStorageConfig
    @Autowired
    private Storage storageService;

    // Nombre del bucket de GCS donde se subirán los archivos
    @Value("${google.cloud.storage.bucket-name}")
    private String bucketName;

    // Bandera para determinar si los archivos subidos deben ser públicos
    // Con el control de acceso uniforme, esta bandera solo influirá en la URL devuelta si el bucket es público.
    @Value("${google.cloud.storage.public.enable}")
    private boolean makePublic;

    /**
     * Sube una imagen codificada en Base64 a un bucket de Google Cloud Storage.
     *
     * @param base64Data La cadena Base64 de la imagen, que puede incluir el prefijo "data:image/..."
     * @param fileName   El nombre deseado para el archivo en GCS (ej: "mi_imagen.png")
     * @return La URL pública del archivo subido en GCS.
     * @throws IOException Si ocurre un error durante la subida o la interacción con la API de GCS.
     */
    public String uploadBase64Image(String base64Data, String fileName) throws IOException {
        log.info("Iniciando subida de imagen a GCS: {}", fileName);

        // 1. Extraer la parte de datos de la cadena Base64
        String base64Image = base64Data;
        if (base64Data.contains(",")) {
            base64Image = base64Data.split(",")[1];
        }

        // 2. Decodificar la cadena Base64 a un array de bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // 3. Determinar el tipo MIME de la imagen
        String mimeType = determineMimeType(base64Data);
        log.debug("Tipo MIME detectado para GCS: {}", mimeType);

        // 4. Crear BlobId y BlobInfo para el archivo en GCS
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(mimeType)
                .build();

        // 5. Subir el archivo a GCS
        storageService.create(blobInfo, imageBytes);
        log.info("Imagen subida exitosamente a GCS. Nombre del objeto: {} en bucket: {}", fileName, bucketName);

        // Con el control de acceso uniforme, no se establecen ACLs a nivel de objeto.
        // El acceso público se gestiona a nivel de bucket.
        if (makePublic) {
            log.info("La configuración 'makePublic' está activa. Asegúrese de que el bucket '{}' tenga permisos de 'Visualizador de objetos de Storage' para 'allUsers' si desea acceso público.", bucketName);
        }

        // 6. Devolver la URL pública del archivo
        return String.format("https://storage.googleapis.com/%s/%s.%s", bucketName, fileName,mimeType);
    }

    /**
     * Determina el tipo MIME de la imagen a partir del prefijo de la cadena Base64.
     *
     * @param base64Data La cadena Base64 completa con el prefijo "data:image/..."
     * @return El tipo MIME detectado (ej: "image/png", "image/jpeg"), o "application/octet-stream" por defecto.
     */
    private String determineMimeType(String base64Data) {
        if (base64Data.startsWith("data:image/jpeg")) {
            return "image/jpeg";
        } else if (base64Data.startsWith("data:image/png")) {
            return "image/png";
        } else if (base64Data.startsWith("data:image/gif")) {
            return "image/gif";
        } else if (base64Data.startsWith("data:image/webp")) {
            return "image/webp";
        }
        log.warn("No se pudo determinar el tipo MIME exacto para GCS. Usando 'application/octet-stream' para la cadena Base64: {}", base64Data.substring(0, Math.min(base64Data.length(), 50)) + "...");
        return "application/octet-stream";
    }
}
    