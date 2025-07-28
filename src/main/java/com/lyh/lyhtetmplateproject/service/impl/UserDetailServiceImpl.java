package com.lyh.lyhtetmplateproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.lyh.lyhtetmplateproject.entity.LoginUser;
import com.lyh.lyhtetmplateproject.entity.domain.User;
import com.lyh.lyhtetmplateproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private UsersService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUsername,username);
        User user = userService.getOne(queryWrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        return new LoginUser(user);
    }

    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getId,id);
        User user = userService.getOne(queryWrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        return new LoginUser(user);
    }
}
