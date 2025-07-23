package com.lyh.chronos.lyhtetmplateproject.service;


import com.lyh.chronos.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.chronos.lyhtetmplateproject.entity.domain.Users;

public interface LoginService {
    ResponseResult login(Users user);

    ResponseResult logout();
}
