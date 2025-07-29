package com.lyh.lyhtetmplateproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.lyhtetmplateproject.entity.domain.Menu;
import com.lyh.lyhtetmplateproject.service.MenuService;
import com.lyh.lyhtetmplateproject.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【menu(权限菜单表)】的数据库操作Service实现
* @createDate 2025-07-28 09:37:28
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




