package com.example.utils;

import com.example.service.LogService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static javax.crypto.Cipher.SECRET_KEY;

/**
 * @author hmxia
 * @date 2025/3/27 9:30
 * JWT工具类
 */
@Component
public class JWTUtils {

    private static String signKey = "aiit";  //签名密钥
    private static long expire = 43200000L;  //有效时间



    //生成令牌

    /**
     * 生成令牌
     * @param claims JWT的第二部分有效负载 中存储的内容，即用户自定义的内容
     * @return
     */
    public static String genJWT(Map<String,Object> claims){

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey)  //签名算法
                .setClaims(claims)  //自定义信息
                .setExpiration(new Date(System.currentTimeMillis() +expire))  //有效期
                .compact();

        return jwt;

    }

    //解析令牌

    /**
     * 解析令牌
     * @param jwt jwt令牌
     * @return
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
       return claims;
    }


}

