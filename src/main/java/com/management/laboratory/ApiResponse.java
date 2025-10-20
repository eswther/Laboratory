package com.management.laboratory;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

    /**
     * 统一API响应格式
     */
@Data
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 请求ID（用于日志追踪）
     */
    private String requestId;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(Boolean success, Integer code, String message, T data) {
        this();
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态工厂方法
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, ResponseCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ApiResponse<T> failure() {
        return new ApiResponse<>(false, ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), null);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, ResponseCode.FAILED.getCode(), message, null);
    }

    public static <T> ApiResponse<T> failure(Integer code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }

    public static <T> ApiResponse<T> failure(ResponseCode responseCode) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), null);
    }

    // 链式调用方法
    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
}



