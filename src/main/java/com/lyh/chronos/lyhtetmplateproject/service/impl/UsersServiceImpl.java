package com.lyh.chronos.lyhtetmplateproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.chronos.lyhtetmplateproject.entity.domain.Users;
import com.lyh.chronos.lyhtetmplateproject.service.UsersService;
import com.lyh.chronos.lyhtetmplateproject.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2025-07-23 11:10:30
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




