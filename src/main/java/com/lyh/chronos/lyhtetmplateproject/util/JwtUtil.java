package com.lyh.chronos.lyhtetmplateproject.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类（适配 io.jsonwebtoken v0.12.5）
 */
public class JwtUtil {

    // 有效期为1小时
    public static final Long JWT_TTL = 60 * 60 * 1000L; // 一小时
    // 设置秘钥明文（注意：Base64编码至少需要32字节用于HS256）
    public static final String JWT_KEY = "3q2+7wIDAQAB3gYFK4EEACIGTQAwFgYIKoEcz1UBgi0hHsT77wIDAQAB";
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成JWT
     *
     * @param subject token中要存放的数据（json格式）
     * @return JWT字符串
     */
    public static String createJWT(String subject) {
        return createJWT(subject, null);
    }

    /**
     * 生成JWT
     *
     * @param subject    token中要存放的数据（json格式）
     * @param ttlMillis  token超时时间
     * @return JWT字符串
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return createJWT(getUUID(), subject, ttlMillis);
    }

    /**
     * 创建token
     *
     * @param id         用户ID
     * @param subject    用户名或其他主体信息
     * @param ttlMillis  token的存活时间，单位为毫秒
     * @return 生成的token字符串
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expDate = new Date(nowMillis + ttlMillis);

        return Jwts.builder()
                .id(id)                      // 唯一ID
                .subject(subject)            // 主题
                .issuer("sg")                // 签发者
                .issuedAt(now)               // 签发时间
                .expiration(expDate)         // 过期时间
                .signWith(secretKey, signatureAlgorithm)
                .compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return SecretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(JWT_KEY.getBytes());
        return Keys.hmacShaKeyFor(encodedKey);
    }

    /**
     * 解析JWT字符串为Claims对象
     *
     * @param jwt 待解析的JWT字符串
     * @return Claims对象
     * @throws Exception 如果JWT解析失败或验证不通过，将抛出异常
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        return parser.parseSignedClaims(jwt).getPayload();
    }

    public static void main(String[] args) throws Exception {
        String jwt = createJWT("2123");
        System.out.println("生成的JWT: " + jwt);

        Claims claims = parseJWT(jwt);
        System.out.println("解析出的Subject: " + claims.getSubject());
    }
}