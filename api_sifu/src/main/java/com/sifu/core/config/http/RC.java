package com.sifu.core.config.http;

public enum RC {

    // 2xx - Éxito
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),

    // 3xx - Redirecciones
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    NOT_MODIFIED(304, "Not Modified"),

    // 4xx - Errores del cliente
    BAD_REQUEST(400, "Bad Request"),
    BAD_REQUEST_VALIDATIONS(400, "Errores de validación"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    // 5xx - Errores del servidor
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout");

    private final int code;
    private final String msg;

    RC(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RC fromCode(int code) {
        for (RC status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código HTTP no reconocido: " + code);
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
