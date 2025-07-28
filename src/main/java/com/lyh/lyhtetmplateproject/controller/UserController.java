package com.lyh.lyhtetmplateproject.controller;

import com.lyh.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.lyhtetmplateproject.entity.domain.User;
import com.lyh.lyhtetmplateproject.entity.dto.UserRegisterDto;
import com.lyh.lyhtetmplateproject.service.LoginService;
import com.lyh.lyhtetmplateproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UsersService userService;
    //登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }

    //登出
    @PostMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    @PostMapping("/register")
    public ResponseResult register(UserRegisterDto user){
        return userService.register(user);
    }

}
