package com.lyh.lyhtetmplateproject.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限菜单关联表
 * @TableName role_menus
 */
@TableName(value ="role_menus")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenus {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单权限ID
     */
    private Long menuId;

    /**
     * 创建时间
     */
    private Date createdAt;
}