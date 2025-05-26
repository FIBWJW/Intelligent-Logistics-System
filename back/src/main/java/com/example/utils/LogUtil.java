package com.example.utils;

import com.example.mapper.LogMapper;
import com.example.pojo.Log;
import com.example.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class LogUtil {

    @Autowired
    private LogMapper LogMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 记录操作日志（从 token 中获取用户信息）
     * @param action 操作内容
     * @param method 请求方法路径，例如 "GET /api/user/list"
     */
    public void recordLog(String action, String method) {
        String token = request.getHeader("token"); // 自定义的 header 字段

        Long userId = null;
        String username = "unknown";

        if (token != null && !token.isEmpty()) {
            try {
                Claims claims = JWTUtils.parseJWT(token);
                userId = Long.valueOf(claims.get("id").toString());
                username = claims.get("username").toString();
            } catch (Exception e) {
                System.out.println("Token 解析失败: " + e.getMessage());
            }
        }

        Log sysLog = new Log();
        sysLog.setUserId(userId);
        sysLog.setUsername(username);
        sysLog.setTimestamp(LocalDateTime.now());
        sysLog.setAction(action);
        sysLog.setIp(getClientIp());
        sysLog.setMethod(method);

        LogMapper.insert(sysLog);
    }

    private String getClientIp() {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

