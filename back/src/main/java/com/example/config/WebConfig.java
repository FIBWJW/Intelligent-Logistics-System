package com.example.config;

import com.example.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

  @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")     // 拦截所有 /api 开头的接口
                .excludePathPatterns("/api/user/login", "/api/register","/api/sugar/zhibiao01","/api/sugar/zhibiao02","/api/sugar/zhibiao03","/api/sugar/zhibiao04");  // 登录、注册接口放行
    }
}