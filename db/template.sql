/*
 * 简化版RBAC权限模型数据库设计
 * 版本：1.0
 * 创建时间：2024
 */

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 数据库创建（如果不存在）
-- ----------------------------
CREATE DATABASE IF NOT EXISTS spring_boot_template DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE spring_boot_template;

-- ----------------------------
-- Table structure for user (用户表)
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`      varchar(50)  NOT NULL COMMENT '用户名',
    `password`      varchar(255) NOT NULL COMMENT '密码',
    `email`         varchar(100)      DEFAULT NULL COMMENT '邮箱',
    `phone`         varchar(20)       DEFAULT NULL COMMENT '手机号',
    `nickname`      varchar(50)       DEFAULT NULL COMMENT '昵称',
    `avatar`        varchar(255)      DEFAULT NULL COMMENT '头像',
    `status`        tinyint(4)        DEFAULT '1' COMMENT '状态：1正常 0禁用',
    `created_at`    timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    timestamp    NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `last_login_at` timestamp    NULL DEFAULT NULL COMMENT '最后登录时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户表';

-- ----------------------------
-- Table structure for role (角色表)
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(50) NOT NULL COMMENT '角色名称',
    `code`        varchar(50) NOT NULL COMMENT '角色编码',
    `description` text COMMENT '角色描述',
    `status`      tinyint(4)       DEFAULT '1' COMMENT '状态：1启用 0禁用',
    `sort_order`  int(11)          DEFAULT '0' COMMENT '排序',
    `created_at`  timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  timestamp   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_name` (`name`),
    UNIQUE KEY `uk_role_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色表';

-- ----------------------------
-- Table structure for menu (权限菜单表)
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`          bigint(20)                   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(100)                 NOT NULL COMMENT '菜单/权限名称',
    `code`        varchar(100)                 NOT NULL COMMENT '权限编码',
    `type`        enum ('menu','button','api') NOT NULL      DEFAULT 'menu' COMMENT '类型：菜单、按钮、API接口',
    `parent_id`   bigint(20)                                 DEFAULT '0' COMMENT '父级ID',
    `path`        varchar(255)                               DEFAULT NULL COMMENT '路由路径',
    `component`   varchar(255)                               DEFAULT NULL COMMENT '组件路径',
    `icon`        varchar(50)                                DEFAULT NULL COMMENT '图标',
    `method`      enum ('GET','POST','PUT','DELETE','PATCH') DEFAULT NULL COMMENT 'HTTP方法',
    `description` text COMMENT '描述',
    `status`      tinyint(4)                                 DEFAULT '1' COMMENT '状态：1启用 0禁用',
    `sort_order`  int(11)                                    DEFAULT '0' COMMENT '排序',
    `created_at`  timestamp                    NULL          DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  timestamp                    NULL          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_menu_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='权限菜单表';

-- ----------------------------
-- Table structure for user_role (用户角色关联表)
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`    bigint(20) NOT NULL COMMENT '用户ID',
    `role_id`    bigint(20) NOT NULL COMMENT '角色ID',
    `created_at` timestamp  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户角色关联表';

-- ----------------------------
-- Table structure for role_menu (角色权限菜单关联表)
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`    bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id`    bigint(20) NOT NULL COMMENT '菜单权限ID',
    `created_at` timestamp  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`),
    CONSTRAINT `fk_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色权限菜单关联表';

-- ----------------------------
-- Indexes (索引)
-- ----------------------------
-- 用户表索引
CREATE INDEX `idx_user_username` ON `user` (`username`);
CREATE INDEX `idx_user_status` ON `user` (`status`);

-- 角色表索引
CREATE INDEX `idx_role_code` ON `role` (`code`);
CREATE INDEX `idx_role_status` ON `role` (`status`);

-- 权限菜单表索引
CREATE INDEX `idx_menu_code` ON `menu` (`code`);
CREATE INDEX `idx_menu_type` ON `menu` (`type`);
CREATE INDEX `idx_menu_parent` ON `menu` (`parent_id`);
CREATE INDEX `idx_menu_status` ON `menu` (`status`);

-- 用户角色关联表索引
CREATE INDEX `idx_user_role_user_id` ON `user_role` (`user_id`);
CREATE INDEX `idx_user_role_role_id` ON `user_role` (`role_id`);

-- 角色权限菜单关联表索引
CREATE INDEX `idx_role_menu_role_id` ON `role_menu` (`role_id`);
CREATE INDEX `idx_role_menu_menu_id` ON `role_menu` (`menu_id`);

-- ----------------------------
-- 初始化基础数据
-- ----------------------------

-- 插入基础角色
INSERT INTO `role` (`name`, `code`, `description`)
VALUES ('超级管理员', 'ROLE_ADMIN', '系统超级管理员，拥有所有权限'),
       ('普通用户', 'ROLE_USER', '普通用户角色'),
       ('访客', 'ROLE_GUEST', '访客角色');

-- 插入基础权限菜单
INSERT INTO `menu` (`name`, `code`, `type`, `parent_id`, `path`, `component`, `icon`, `method`, `description`)
VALUES ('系统管理', 'MENU_SYSTEM', 'menu', 0, '/system', 'Layout', 'system', NULL, '系统管理'),
       ('用户管理', 'MENU_USER', 'menu', 1, '/system/user', 'system/user/index', 'user', NULL, '用户管理'),
       ('用户列表', 'USER_LIST', 'button', 2, NULL, NULL, NULL, 'GET', '查看用户列表'),
       ('新增用户', 'USER_ADD', 'button', 2, NULL, NULL, NULL, 'POST', '新增用户'),
       ('编辑用户', 'USER_EDIT', 'button', 2, NULL, NULL, NULL, 'PUT', '编辑用户'),
       ('删除用户', 'USER_DELETE', 'button', 2, NULL, NULL, NULL, 'DELETE', '删除用户'),
       ('角色管理', 'MENU_ROLE', 'menu', 1, '/system/role', 'system/role/index', 'peoples', NULL, '角色管理'),
       ('角色列表', 'ROLE_LIST', 'button', 6, NULL, NULL, NULL, 'GET', '查看角色列表'),
       ('新增角色', 'ROLE_ADD', 'button', 6, NULL, NULL, NULL, 'POST', '新增角色'),
       ('编辑角色', 'ROLE_EDIT', 'button', 6, NULL, NULL, NULL, 'PUT', '编辑角色'),
       ('删除角色', 'ROLE_DELETE', 'button', 6, NULL, NULL, NULL, 'DELETE', '删除角色');

-- 为超级管理员分配所有权限菜单
INSERT INTO `role_menu` (`role_id`, `menu_id`)
SELECT 1, `id`
FROM `menu`;

-- 插入测试用户 (密码为：123456，已加密)
INSERT INTO `user` (`username`, `password`, `email`, `nickname`)
VALUES ('admin', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'admin@example.com', '超级管理员'),
       ('user', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'user@example.com', '普通用户');

-- 为测试用户分配角色
INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 1), -- admin用户分配超级管理员角色
       (2, 2);
-- user用户分配普通用户角色

-- ----------------------------
-- 常用查询视图
-- ----------------------------

-- 用户权限视图
DROP VIEW IF EXISTS `v_user_permissions`;
CREATE VIEW `v_user_permissions` AS
SELECT u.id        AS user_id,
       u.username,
       u.nickname,
       r.id        AS role_id,
       r.name      AS role_name,
       r.code      AS role_code,
       m.id        AS menu_id,
       m.name      AS menu_name,
       m.code      AS menu_code,
       m.type      AS menu_type,
       m.path      AS menu_path,
       m.component AS menu_component,
       m.method    AS menu_method
FROM user u
         JOIN user_role ur ON u.id = ur.user_id
         JOIN role r ON ur.role_id = r.id
         JOIN role_menu rm ON r.id = rm.role_id
         JOIN menu m ON rm.menu_id = m.id
WHERE u.status = 1
  AND r.status = 1
  AND m.status = 1;

-- 用户角色视图
DROP VIEW IF EXISTS `v_user_role`;
CREATE VIEW `v_user_role` AS
SELECT u.id          AS user_id,
       u.username,
       u.nickname,
       r.id          AS role_id,
       r.name        AS role_name,
       r.code        AS role_code,
       r.description AS role_description
FROM user u
         JOIN user_role ur ON u.id = ur.user_id
         JOIN role r ON ur.role_id = r.id
WHERE u.status = 1
  AND r.status = 1;

SET FOREIGN_KEY_CHECKS = 1;