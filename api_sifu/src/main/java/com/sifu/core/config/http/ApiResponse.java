package com.sifu.core.config.http;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL) // Omite campos nulos en el JSON
public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    // Constructor privado para forzar uso de métodos estáticos
    private ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ApiResponse(RC rc, T data) {
        this.code = rc.code();
        this.msg = rc.msg();
        this.data = data;
    }

    // Métodos factory para éxito/error
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(RC.OK.code(), RC.OK.msg(), data);
    }

    public static <T> ApiResponse<T> success(RC rc, T data) {
        return new ApiResponse<>(rc.code(), rc.msg(), data);
    }

    public static <T> ApiResponse<T> success(RC rc, String message, T data) {
        return new ApiResponse<>(rc.code(), message, data);
    }

    public static <T> ApiResponse<T> internalError() {
        return new ApiResponse<>(RC.INTERNAL_SERVER_ERROR.code(), RC.INTERNAL_SERVER_ERROR.msg(), null);
    }

    public static <T> ApiResponse<T> internalError(String message) {
        return new ApiResponse<>(RC.INTERNAL_SERVER_ERROR.code(), message, null);
    }

    public static <T> ApiResponse<T> error(RC rc) {
        return new ApiResponse<>(rc.code(), rc.msg(), null);
    }

    public static <T> ApiResponse<T> error(RC rc, String message) {
        return new ApiResponse<>(rc.code(), message, null);
    }

    public static <T> ApiResponse<T> error(RC rc, T messages) {
        return new ApiResponse<>(rc.code(), rc.msg(), messages);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

