package com.lyh.lyhtetmplateproject.filter;

import com.alibaba.fastjson2.JSONObject;

import com.lyh.lyhtetmplateproject.config.WebSecurityProperties;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private WebSecurityProperties webSecurityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String token = request.getHeader("Authorization");

        // 检查请求路径是否在放行列表中
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

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
        
        // Token一致性校验
        String redisToken = null;
        if (cacheObject instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) cacheObject;
            LoginUser loginUser = jsonObject.toJavaObject(LoginUser.class);
            // 继续处理...
            if (Objects.isNull(loginUser)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户未登录或无权限");
                return;
            }
            
            // 从Redis中获取存储的token进行一致性校验
            redisToken = (String) redisCache.getCacheObject("token:" + userid);
            if (redisToken == null || !redisToken.equals(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token已失效，请重新登录");
                return;
            }

            // 延长 Redis 中的登录信息过期时间
            redisCache.expire(redisKey, 60 * 60 * 24, TimeUnit.SECONDS); // 延长一天
            // 延长token的过期时间
            redisCache.expire("token:" + userid, 60 * 60 * 24, TimeUnit.SECONDS);

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
            
            // 从Redis中获取存储的token进行一致性校验
            redisToken = (String) redisCache.getCacheObject("token:" + userid);
            if (redisToken == null || !redisToken.equals(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token已失效，请重新登录");
                return;
            }

            // 延长 Redis 中的登录信息过期时间
            redisCache.expire(redisKey, 60 * 60 * 24, TimeUnit.SECONDS); // 延长一天
            // 延长token的过期时间
            redisCache.expire("token:" + userid, 60 * 60 * 24, TimeUnit.SECONDS);

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

    /**
     * 判断当前请求是否不需要进行JWT验证
     * @param request 当前HTTP请求
     * @return 是否不需要进行JWT验证
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> permitAllPaths = webSecurityProperties.getPermitAllPaths();
        if (permitAllPaths != null) {
            return permitAllPaths.stream().anyMatch(path -> {
                // 使用AntPathMatcher进行路径匹配
                return antPathMatcher.match(path, request.getServletPath());
            });
        }
        return false;
    }
}