package com.lyh.lyhtetmplateproject.service;


import com.lyh.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.lyhtetmplateproject.entity.domain.Users;

public interface LoginService {
    ResponseResult login(Users user);

    ResponseResult logout();
}
