package com.lyh.lyhtetmplateproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.lyhtetmplateproject.entity.domain.UserRole;
import com.lyh.lyhtetmplateproject.service.UserRoleService;
import com.lyh.lyhtetmplateproject.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【user_role(用户角色关联表)】的数据库操作Service实现
* @createDate 2025-07-28 09:37:28
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




