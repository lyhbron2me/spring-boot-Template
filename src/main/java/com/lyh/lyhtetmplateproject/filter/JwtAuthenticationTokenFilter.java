package com.lyh.lyhtetmplateproject.filter;

import com.alibaba.fastjson2.JSONObject;

import com.lyh.lyhtetmplateproject.entity.LoginUser;
import com.lyh.lyhtetmplateproject.util.RedisCache;
import com.lyh.lyhtetmplateproject.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析 token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token 非法");
            return;
        }
        // 从 redis 中获取用户信息
        String redisKey = "login:" + userid;
        Object cacheObject = redisCache.getCacheObject(redisKey);
        if (cacheObject == null) {
            // 如果 cacheObject 为 null，返回 403 Forbidden
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录或无权限");
            return;
        }
        if (cacheObject instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) cacheObject;
            LoginUser loginUser = jsonObject.toJavaObject(LoginUser.class);
            // 继续处理...
            if (Objects.isNull(loginUser)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录或无权限");
                return;
            }

            // 延长 Redis 中的登录信息过期时间
            redisCache.expire(redisKey, 60 * 60 * 24, TimeUnit.SECONDS); // 延长一天

            // 存入 SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 放行
            filterChain.doFilter(request, response);
        } else if (cacheObject instanceof String) {
            String jsonStr = (String) cacheObject;
            LoginUser loginUser = JSONObject.parseObject(jsonStr, LoginUser.class);
            // 继续处理...
            if (Objects.isNull(loginUser)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录或无权限");
                return;
            }

            // 延长 Redis 中的登录信息过期时间
            redisCache.expire(redisKey, 60 * 60 * 24, TimeUnit.SECONDS); // 延长一天

            // 存入 SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 放行
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected type from Redis: " + cacheObject.getClass().getName());
        }
    }
}

