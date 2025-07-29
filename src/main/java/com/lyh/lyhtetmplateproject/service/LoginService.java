package com.lyh.lyhtetmplateproject.service;


import com.lyh.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.lyhtetmplateproject.entity.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
