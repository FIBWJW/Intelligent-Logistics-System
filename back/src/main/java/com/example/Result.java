package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    // 成功（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    // 成功（无数据）
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    // 错误（自定义信息和状态码）
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    // 错误（默认状态码）
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    // 参数错误
    public static <T> Result<T> badRequest(String msg) {
        return new Result<>(400, msg, null);
    }

    // 未授权
    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(401, msg, null);
    }

    // 权限不足
    public static <T> Result<T> forbidden(String msg) {
        return new Result<>(403, msg, null);
    }
}
