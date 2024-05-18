SET
	NAMES utf8mb4; 
	
-- 1、部门表
DROP
	TABLE IF EXISTS sys_dept; CREATE TABLE sys_dept (
		dept_id varchar(20) NOT NULL COMMENT '部门id',
		parent_id varchar(20) DEFAULT 0 COMMENT '父部门id',
		ancestors varchar(50) DEFAULT '' COMMENT '祖级列表',
		dept_name varchar(30) DEFAULT '' COMMENT '部门名称',
		order_num int(4) DEFAULT 0 COMMENT '显示顺序',
		leader varchar(20) DEFAULT NULL COMMENT '负责人',
		phone varchar(11) DEFAULT NULL COMMENT '联系电话',
		email varchar(50) DEFAULT NULL COMMENT '邮箱',
		STATUS CHAR(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
		create_by varchar(64) DEFAULT '' COMMENT '创建者',
		create_time datetime COMMENT '创建时间',
		update_by varchar(64) DEFAULT '' COMMENT '更新者',
		update_time datetime COMMENT '更新时间',
		PRIMARY KEY (dept_id)
	) ENGINE = innodb COMMENT = '部门表'; 
	
-- 2、用户信息表
DROP
	TABLE IF EXISTS sys_user; CREATE TABLE sys_user (
		user_id varchar(20) NOT NULL COMMENT '用户ID',
		dept_id varchar(20) DEFAULT NULL COMMENT '部门ID',
		user_name varchar(30) NOT NULL COMMENT '用户账号',
		nick_name varchar(30) NOT NULL COMMENT '用户昵称',
		user_type varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
		email varchar(50) DEFAULT '' COMMENT '用户邮箱',
		phone varchar(11) DEFAULT '' COMMENT '联系电话',
		sex CHAR(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
		avatar varchar(100) DEFAULT '' COMMENT '头像地址',
		PASSWORD varchar(100) DEFAULT '' COMMENT '密码',
		STATUS CHAR(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
		login_ip varchar(128) DEFAULT '' COMMENT '最后登录IP',
		login_date datetime COMMENT '最后登录时间',
		create_by varchar(64) DEFAULT '' COMMENT '创建者',
		create_time datetime COMMENT '创建时间',
		update_by varchar(64) DEFAULT '' COMMENT '更新者',
		update_time datetime COMMENT '更新时间',
		remark varchar(500) DEFAULT NULL COMMENT '备注',
		PRIMARY KEY (user_id)
	) ENGINE = innodb COMMENT = '用户信息表'; 
	
-- 3、岗位信息表
DROP
	TABLE IF EXISTS sys_post; CREATE TABLE sys_post (
		post_id varchar(20) NOT NULL COMMENT '岗位ID',
		post_code varchar(64) NOT NULL COMMENT '岗位编码',
		post_name varchar(50) NOT NULL COMMENT '岗位名称',
		post_sort int(4) NOT NULL COMMENT '显示顺序',
		STATUS CHAR(1) NOT NULL COMMENT '状态（0正常 1停用）',
		create_by varchar(64) DEFAULT '' COMMENT '创建者',
		create_time datetime COMMENT '创建时间',
		update_by varchar(64) DEFAULT '' COMMENT '更新者',
		update_time datetime COMMENT '更新时间',
		remark varchar(500) DEFAULT NULL COMMENT '备注',
		PRIMARY KEY (post_id)
	) ENGINE = innodb COMMENT = '岗位信息表'; 
	
-- 4、角色信息表
DROP
	TABLE IF EXISTS sys_role; CREATE TABLE sys_role (
		role_id varchar(20) NOT NULL COMMENT '角色ID',
		role_name varchar(30) NOT NULL COMMENT '角色名称',
		role_key varchar(100) NOT NULL COMMENT '角色权限字符串',
		role_sort int(4) NOT NULL COMMENT '显示顺序',
		data_scope CHAR(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
		menu_check_strictly tinyint(1) DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
		dept_check_strictly tinyint(1) DEFAULT 1 COMMENT '部门树选择项是否关联显示',
		STATUS CHAR(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
		create_by varchar(64) DEFAULT '' COMMENT '创建者',
		create_time datetime COMMENT '创建时间',
		update_by varchar(64) DEFAULT '' COMMENT '更新者',
		update_time datetime COMMENT '更新时间',
		remark varchar(500) DEFAULT NULL COMMENT '备注',
		PRIMARY KEY (role_id)
	) ENGINE = innodb COMMENT = '角色信息表'; 
	
-- 5、菜单权限表
DROP
	TABLE IF EXISTS sys_menu; CREATE TABLE sys_menu (
		menu_id varchar(20) NOT NULL COMMENT '菜单ID',
		menu_name varchar(50) NOT NULL COMMENT '菜单名称',
		parent_id varchar(20) DEFAULT '0' COMMENT '父菜单ID',
		order_num int(4) DEFAULT 0 COMMENT '显示顺序',
		path varchar(200) DEFAULT '' COMMENT '路由地址',
		component varchar(255) DEFAULT NULL COMMENT '组件路径',
		query varchar(255) DEFAULT NULL COMMENT '路由参数',
		is_frame int(1) DEFAULT 1 COMMENT '是否为外链（0是 1否）',
		is_cache int(1) DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
		menu_type CHAR(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
		visible CHAR(1) DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
		STATUS CHAR(1) DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
		perms varchar(100) DEFAULT NULL COMMENT '权限标识',
		icon varchar(100) DEFAULT '#' COMMENT '菜单图标',
		create_by varchar(64) DEFAULT '' COMMENT '创建者',
		create_time datetime COMMENT '创建时间',
		update_by varchar(64) DEFAULT '' COMMENT '更新者',
		update_time datetime COMMENT '更新时间',
		remark varchar(500) DEFAULT '' COMMENT '备注',
		PRIMARY KEY (menu_id)
	) ENGINE = innodb COMMENT = '菜单权限表'; 
	
-- 6、用户和角色关联表  用户N-1角色
DROP
	TABLE IF EXISTS sys_user_role; CREATE TABLE sys_user_role (
		user_id bigint(20) NOT NULL COMMENT '用户ID',
		role_id bigint(20) NOT NULL COMMENT '角色ID',
		PRIMARY KEY(user_id, role_id)
	) ENGINE = innodb COMMENT = '用户和角色关联表'; 
	
-- 7、角色和菜单关联表  角色1-N菜单
DROP
	TABLE IF EXISTS sys_role_menu; CREATE TABLE sys_role_menu (
		role_id bigint(20) NOT NULL COMMENT '角色ID',
		menu_id bigint(20) NOT NULL COMMENT '菜单ID',
		PRIMARY KEY(role_id, menu_id)
	) ENGINE = innodb COMMENT = '角色和菜单关联表';