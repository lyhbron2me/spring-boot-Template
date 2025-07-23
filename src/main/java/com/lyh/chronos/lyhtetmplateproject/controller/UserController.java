package com.lyh.chronos.lyhtetmplateproject.controller;

import com.lyh.chronos.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.chronos.lyhtetmplateproject.entity.domain.Users;
import com.lyh.chronos.lyhtetmplateproject.entity.dto.UserRegisterDto;
import com.lyh.chronos.lyhtetmplateproject.service.LoginService;
import com.lyh.chronos.lyhtetmplateproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseResult login(Users user){
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
