package com.lyh.lyhtetmplateproject.service;

import com.lyh.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.lyhtetmplateproject.entity.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.lyhtetmplateproject.entity.dto.UserRegisterDto;

/**
* @author Lenovo
* @description 针对表【users(用户表)】的数据库操作Service
* @createDate 2025-07-23 11:10:30
*/
public interface UsersService extends IService<User> {


    ResponseResult register(UserRegisterDto user);
}
