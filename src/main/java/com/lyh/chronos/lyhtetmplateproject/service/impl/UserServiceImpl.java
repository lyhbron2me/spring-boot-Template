package com.lyh.chronos.lyhtetmplateproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.chronos.lyhtetmplateproject.entity.domain.User;
import com.lyh.chronos.lyhtetmplateproject.service.UserService;
import com.lyh.chronos.lyhtetmplateproject.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-07-22 17:09:42
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




