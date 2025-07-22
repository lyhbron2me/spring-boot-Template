CREATE DATABASE IF NOT EXISTS `spring_boot_template`;

USE `spring_boot_template`;

-- 用户表
CREATE TABLE `user`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name`     VARCHAR(64) NOT NULL COMMENT '用户名',
    `password`      VARCHAR(255) NOT NULL COMMENT '密码',
    `email`         VARCHAR(64) NOT NULL COMMENT '邮箱',
    `create_time`   DATETIME             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag`      INT(1)               DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    `last_login_time` DATETIME           DEFAULT NULL COMMENT '最后登录时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_name` (`user_name`),
    INDEX `idx_del_flag` (`del_flag`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 角色表
CREATE TABLE `role`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
    `role_name`   VARCHAR(30) NOT NULL COMMENT '角色名称',
    `role_key`    VARCHAR(100) NOT NULL COMMENT '角色标识',
    `role_sort`   INT(4) NOT NULL COMMENT '显示顺序',
    `status`      CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `del_flag`    CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    `create_by`   BIGINT(20) DEFAULT NULL COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   BIGINT(20) DEFAULT NULL COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    INDEX `idx_create_by` (`create_by`),
    INDEX `idx_update_by` (`update_by`),
    INDEX `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT ='角色表';

-- 菜单权限表
CREATE TABLE `menu`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
    `menu_name`   VARCHAR(50) NOT NULL COMMENT '菜单名称',
    `parent_id`   BIGINT(20) DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   INT(4) DEFAULT 0 COMMENT '显示顺序',
    `path`        VARCHAR(200) DEFAULT '' COMMENT '路由地址',
    `component`   VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    `menu_type`   CHAR(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     CHAR(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      CHAR(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    `icon`        VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    `create_by`   BIGINT(20) DEFAULT NULL COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   BIGINT(20) DEFAULT NULL COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag`    CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`),
    INDEX `idx_create_by` (`create_by`),
    INDEX `idx_update_by` (`update_by`),
    INDEX `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=2033 DEFAULT CHARSET=utf8mb4 COMMENT ='菜单权限表';

-- 用户角色表
CREATE TABLE `user_role`
(
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT ='用户角色表';

-- 角色权限表
CREATE TABLE `role_menu`
(
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    `menu_id` BIGINT(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT ='角色权限表';