package com.example.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Result; // 你的Result统一返回类
import com.example.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper(); // 用来转JSON字符串

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (token == null || token.isEmpty()) {
            return handleNoLogin(response, "未登录，请先登录");
        }

        try {
            Claims claims = JWTUtils.parseJWT(token);

            String role = (String) claims.get("role");  // 从 token 中获取角色字段

            if (!"ADMIN".equals(role)) {
                return handleNoLogin(response, "无权限访问，仅限管理员");
            }

            return true;  // 是 ADMIN，放行
        } catch (Exception e) {
            return handleNoLogin(response, "登录信息无效或已过期");
        }
    }

    // 改造 handleNoLogin 方法支持自定义提示信息
    private boolean handleNoLogin(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        Result<String> result = new Result<>(401, message, null);
        String json = objectMapper.writeValueAsString(result);

        response.getWriter().write(json);
        return false;
    }
}
