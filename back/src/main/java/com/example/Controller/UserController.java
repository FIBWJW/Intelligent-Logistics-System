package com.example.Controller;

import com.example.Result;
import com.example.pojo.LoginDTO;
import com.example.pojo.User;
import com.example.mapper.UserMapper;
import com.example.pojo.Log;
import com.example.service.LogService;
import com.example.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private LogService logService;

    @Resource
    private HttpServletRequest request; // 用于获取客户端IP

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 1. 查用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            // 记录失败的登录尝试
            recordLoginLog(null, username, "登录失败: 用户不存在");
            return Result.error("用户不存在");
        }

        // 2. 校验密码
        if (!user.getPassword().equals(password)) {
            // 记录失败的登录尝试
            recordLoginLog(user.getId(), username, "登录失败: 密码错误");
            return Result.error("密码错误");
        }

        // 3. 生成JWT token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());

        String token = JWTUtils.genJWT(claims);

        // 4. 存入Redis（设置1小时有效）
        redisTemplate.opsForValue().set("login:" + token, user, 1, TimeUnit.HOURS);

        // 5. 记录成功的登录
        recordLoginLog(user.getId(), username, "登录成功");

        // 6. 返回前端数据
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("username", user.getUsername());
        resultData.put("role", user.getRole());
        resultData.put("token", token);

        return Result.success(resultData);
    }

    /**
     * 记录登录日志
     * @param userId 用户ID
     * @param username 用户名
     * @param action 操作内容
     */
    private void recordLoginLog(Long userId, String username, String action) {
        Log log = new Log();
        log.setUsername(username != null ? username : "unknown");
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setIp(getClientIp());
        log.setMethod("POST /api/user/login");

        if (userId != null) {
            log.setUserId(userId); // 假设Log实体中有userId字段
        }

        logService.addLog(log);
    }

    /**
     * 获取客户端IP地址
     */
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