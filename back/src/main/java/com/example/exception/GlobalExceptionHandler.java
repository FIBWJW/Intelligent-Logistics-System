package com.example.exception;

import com.example.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // 统一异常处理，返回JSON
public class GlobalExceptionHandler {

    // 处理所有 Exception
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();  // 方便后台开发时看错误日志

        return new Result<>(500, "服务器内部错误，请联系管理员", null);
    }

    // 可以加一个专门处理运行时异常的（可选）
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return new Result<>(500, "运行时异常：" + e.getMessage(), null);
    }

    // 可以针对自定义异常（如果你有的话）处理，比如
    /*
    @ExceptionHandler(CustomException.class)
    public Result<String> handleCustomException(CustomException e) {
        return new Result<>(e.getCode(), e.getMessage(), null);
    }
    */
}
