package com.lyh.lyhtetmplateproject.service.impl;

import com.lyh.lyhtetmplateproject.entity.LoginUser;
import com.lyh.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.lyhtetmplateproject.entity.domain.User;
import com.lyh.lyhtetmplateproject.service.LoginService;
import com.lyh.lyhtetmplateproject.util.JwtUtil;
import com.lyh.lyhtetmplateproject.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authentication)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser,60 * 60 * 24, TimeUnit.SECONDS);
        // 将token存入redis用于一致性校验
        redisCache.setCacheObject("token:"+userId, jwt, 60 * 60 * 24, TimeUnit.SECONDS);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登陆成功",map);
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("login:"+userid);
        // 删除token
        redisCache.deleteObject("token:"+userid);
        return ResponseResult.okResult(200,"注销成功");
    }

}