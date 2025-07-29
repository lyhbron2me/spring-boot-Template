package com.lyh.lyhtetmplateproject.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 权限菜单表
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 菜单/权限名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 类型：菜单、按钮、API接口
     */
    private Object type;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * HTTP方法
     */
    private Object method;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：1启用 0禁用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}