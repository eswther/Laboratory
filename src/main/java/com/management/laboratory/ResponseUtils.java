package com.management.laboratory;

/**
 * 响应工具类
 */
public class ResponseUtils {

    private ResponseUtils() {
        // 工具类，私有构造函数，防止实例化
    }

    public static <T> ApiResponse<T> ok() {
        return ApiResponse.success();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.success(data);
    }

    public static <T> ApiResponse<T> ok(String message) {
        return ApiResponse.success(message, null);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.success(message, data);
    }

    public static <T> ApiResponse<T> fail() {
        return ApiResponse.failure();
    }

    public static <T> ApiResponse<T> fail(String message) {
        return ApiResponse.failure(message);
    }

    public static <T> ApiResponse<T> fail(Integer code, String message) {
        return ApiResponse.failure(code, message);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {
        return ApiResponse.failure(responseCode);
    }
}
