package com.example.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Log {
    private Long id;
    private Long userId;
    private String username;    // 操作人
    private String action;      // 操作内容
    private LocalDateTime timestamp; // 操作时间
    private String ip;          // 操作IP（可选）
    private String method;      // 操作方法（可选）
}