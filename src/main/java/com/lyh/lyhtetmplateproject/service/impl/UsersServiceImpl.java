package com.lyh.lyhtetmplateproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.lyhtetmplateproject.enmus.AppHttpCodeEnum;
import com.lyh.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.lyhtetmplateproject.entity.domain.Users;
import com.lyh.lyhtetmplateproject.entity.dto.UserRegisterDto;
import com.lyh.lyhtetmplateproject.service.UsersService;
import com.lyh.lyhtetmplateproject.mapper.UsersMapper;
import com.lyh.lyhtetmplateproject.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2025-07-23 11:10:30
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(UserRegisterDto userDto) {
        //验证验证码是否合法
//        String code = redisCache.getCacheObject("emailCode:" + userDto.getEmail());
        // 检查验证码是否存在
//        if (code == null) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.CODE_ERROR);
//        }

        // 检查验证码是否匹配
//        if (!code.equals(userDto.getCode())) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.CODE_ERROR);
//        }
        // 检查用户名是否已存在
        if (getOne(new UpdateWrapper<Users>().eq("username", userDto.getUsername())) != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        // 检查邮箱是否已存在
//        if (getOne(new UpdateWrapper<User>().eq("email", userDto.getEmail())) != null) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
//        }
        // 密码加密
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        Users user = BeanCopyUtils.copyBean(userDto, Users.class);
        save(user);
        //删除验证码
//        redisCache.deleteObject("emailCode:" + userDto.getEmail());
        return ResponseResult.okResult();
    }
}




