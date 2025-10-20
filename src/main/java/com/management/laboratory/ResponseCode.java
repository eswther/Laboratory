package com.management.laboratory;

/**
 * 响应码枚举
 */
public enum ResponseCode {

    // 成功
    SUCCESS(200, "操作成功"),
    // 失败
    FAILED(500, "操作失败"),
    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    DATABASE_ERROR(409, "数据库错误"),

    // 服务器错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    // 业务错误
    USER_NOT_EXIST(1001, "用户不存在"),
    USER_EXIST(1002, "用户已存在"),
    LOGIN_FAILED(1003, "登录失败"),
    PASSWORD_ERROR(1004, "密码错误"),
    TOKEN_EXPIRED(1005, "Token已过期"),
    TOKEN_INVALID(1006, "Token无效"),
    TEACHER_NOT_EXIST(1007, "教师不存在"),
    TEACHER_EXIST(1008, "教师已存在"),
    STUDENT_NOT_EXIST(1007, "学生已存在"),
    STUDENT_EXIST(1008, "学生不存在"),

    // 实验室业务错误
    LAB_NOT_EXIST(2001, "实验室不存在"),
    LAB_UNAVAILABLE(2002, "实验室不可用"),
    EQUIPMENT_NOT_EXIST(2003, "设备不存在"),
    EQUIPMENT_UNAVAILABLE(2004, "设备不可用"),
    RESERVATION_CONFLICT(2005, "预约时间冲突"),
    RESERVATION_NOT_EXIST(2006, "预约不存在"),
    MAINTENANCE_NOT_EXIST(2007, "维修记录不存在"),
    APPROVAL_NOT_EXIST(2008, "审批记录不存在");

    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
