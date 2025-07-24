package com.lyh.lyhtetmplateproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.lyhtetmplateproject.entity.domain.UserRoles;
import com.lyh.lyhtetmplateproject.service.UserRolesService;
import com.lyh.lyhtetmplateproject.mapper.UserRolesMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【user_roles(用户角色关联表)】的数据库操作Service实现
* @createDate 2025-07-23 11:10:30
*/
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles>
    implements UserRolesService{

}




