-- 创建数据库
CREATE DATABASE IF NOT EXISTS `kun_system`;

-- 切换数据库
USE `kun_system`;

-- 设置编码
SET NAMES UTF8MB4;

-- 部门表
DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
    `dept_id` VARCHAR(20) NOT NULL COMMENT '部门id',
    `parent_id` VARCHAR(20) DEFAULT 0 COMMENT '父部门id',
    `ancestors` VARCHAR(50) DEFAULT '' COMMENT '祖级列表',
    `dept_name` VARCHAR(30) DEFAULT '' COMMENT '部门名称',
    `order_num` INT(4) DEFAULT 0 COMMENT '显示顺序',
    `leader` VARCHAR(20) DEFAULT NULL COMMENT '负责人',
    `phone` VARCHAR(11) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
    `status` CHAR(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    PRIMARY KEY (`dept_id`)
) ENGINE = innodb COMMENT = '部门表';
    
-- 用户信息表
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
    `user_id` VARCHAR(20) NOT NULL COMMENT '用户ID',
    `dept_id` VARCHAR(20) DEFAULT NULL COMMENT '部门ID',
    `user_name` VARCHAR(30) NOT NULL COMMENT '用户账号',
    `nick_name` VARCHAR(30) NOT NULL COMMENT '用户昵称',
    `user_type` VARCHAR(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `email` VARCHAR(50) DEFAULT '' COMMENT '用户邮箱',
    `phone` VARCHAR(11) DEFAULT '' COMMENT '联系电话',
    `sex` CHAR(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar` VARCHAR(100) DEFAULT '' COMMENT '头像地址',
    `password` VARCHAR(100) DEFAULT '' COMMENT '密码',
    `status` CHAR(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `login_ip` VARCHAR(128) DEFAULT '' COMMENT '最后登录IP',
    `login_date` DATETIME COMMENT '最后登录时间',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_id`)
) ENGINE = innodb COMMENT = '用户信息表';
    
-- 岗位信息表
DROP TABLE IF EXISTS `sys_post`;

CREATE TABLE `sys_post` (
    `post_id` VARCHAR(20) NOT NULL COMMENT '岗位ID',
    `post_code` VARCHAR(64) NOT NULL COMMENT '岗位编码',
    `post_name` VARCHAR(50) NOT NULL COMMENT '岗位名称',
    `post_sort` INT(4) NOT NULL COMMENT '显示顺序',
    `status` CHAR(1) NOT NULL COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`post_id`)
) ENGINE = innodb COMMENT = '岗位信息表';
    
-- 角色信息表
DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
    `role_id` VARCHAR(20) NOT NULL COMMENT '角色ID',
    `role_name` VARCHAR(30) NOT NULL COMMENT '角色名称',
    `role_key` VARCHAR(100) NOT NULL COMMENT '角色权限字符串',
    `role_sort` INT(4) NOT NULL COMMENT '显示顺序',
    `data_scope` CHAR(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `menu_check_strictly` TINYINT(1) DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
    `dept_check_strictly` TINYINT(1) DEFAULT 1 COMMENT '部门树选择项是否关联显示',
    `status` CHAR(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`)
) ENGINE = innodb COMMENT = '角色信息表';
    
-- 菜单权限表
DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
    `menu_id` VARCHAR(20) NOT NULL COMMENT '菜单ID',
    `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
    `parent_id` VARCHAR(20) DEFAULT '0' COMMENT '父菜单ID',
    `order_num` INT(4) DEFAULT 0 COMMENT '显示顺序',
    `path` VARCHAR(200) DEFAULT '' COMMENT '路由地址',
    `component` VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    `query` VARCHAR(255) DEFAULT NULL COMMENT '路由参数',
    `is_frame` INT(1) DEFAULT 1 COMMENT '是否为外链（0是 1否）',
    `is_cache` INT(1) DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
    `menu_type` CHAR(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible` CHAR(1) DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
    `status` CHAR(1) DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
    `perms` VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    `icon` VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`)
) ENGINE = innodb COMMENT = '菜单权限表';
    
-- 用户和角色关联表
DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY(`user_id`, `role_id`)
) ENGINE = innodb COMMENT = '用户和角色关联表';
    
-- 角色和菜单关联表
DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    `menu_id` BIGINT(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY(`role_id`, `menu_id`)
) ENGINE = innodb COMMENT = '角色和菜单关联表';

-- 字典类型表
DROP TABLE IF EXISTS `sys_dict_type`; 

CREATE TABLE `sys_dict_type` (
    `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
    `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
    `status` CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime COMMENT '更新时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_id`),
    UNIQUE (`dict_type`)
) ENGINE = innodb AUTO_INCREMENT = 100 COMMENT = '字典类型表';

-- 3、操作日志记录
DROP TABLE IF EXISTS `sys_oper_log`; 

CREATE TABLE `sys_oper_log` (
    `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `title` varchar(50) DEFAULT '' COMMENT '模块标题',
    `business_type` int(2) DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `method` varchar(100) DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
    `operator_type` int(1) DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
    `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
    `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
    `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
    `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
    `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
    `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
    `status` int(1) DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
    `oper_time` datetime COMMENT '操作时间',
    `cost_time` bigint(20) DEFAULT 0 COMMENT '消耗时间',
    PRIMARY KEY (`oper_id`),
    KEY idx_sys_oper_log_bt (`business_type`),
    KEY idx_sys_oper_log_s (`status`),
    KEY idx_sys_oper_log_ot (`oper_time`)
) ENGINE = innodb AUTO_INCREMENT = 100 COMMENT = '操作日志记录';