/*
Navicat MySQL Data Transfer

Source Server         : lemon
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : coredb

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-10-18 18:33:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键dept_id',
  `parent_dept_id` int(11) NOT NULL COMMENT '父部门parent_dept_id',
  `dept_simple_name` varchar(45) NOT NULL COMMENT '简称',
  `dept_full_name` varchar(255) NOT NULL COMMENT '全称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

insert into t_dept(dept_id,parent_dept_id,dept_simple_name,dept_full_name,description,version)
values(1,1,'总裁办公室','总裁办公室','',1);

-- ----------------------------
-- Records of t_dept
-- ----------------------------

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dict_code` varchar(255) NOT NULL COMMENT '代码',
  `dict_name` varchar(255) NOT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of t_dict
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_id` int(11) NOT NULL COMMENT '菜单组id',
  `menu_code` varchar(255) NOT NULL COMMENT '菜单编号',
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `icon` varchar(255) NOT NULL COMMENT '菜单图标',
  `url` varchar(255) NOT NULL COMMENT 'url地址',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) NOT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `order_by` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '2', 'deptManage', '部门管理', '', '/dept/main', '', '1', '1');

-- ----------------------------
-- Table structure for t_menu_group
-- ----------------------------
DROP TABLE IF EXISTS `t_menu_group`;
CREATE TABLE `t_menu_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键group_id',
  `group_name` varchar(255) NOT NULL COMMENT '组名称',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `order_by` int(11) NOT NULL COMMENT '排序',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  `icon` varchar(255) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='菜单组表';

-- ----------------------------
-- Records of t_menu_group
-- ----------------------------
INSERT INTO `t_menu_group` VALUES ('1', '个人中心', '个人中心', '1', '1', '');
INSERT INTO `t_menu_group` VALUES ('2', '系统设置', '系统设置', '2', '1', 'fa fa-tv');

-- ----------------------------
-- Table structure for t_right
-- ----------------------------
DROP TABLE IF EXISTS `t_right`;
CREATE TABLE `t_right` (
  `right_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键right_id',
  `right_url` varchar(255) NOT NULL COMMENT '权限url',
  `right_name` varchar(255) NOT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`right_id`),
  KEY `fk_right_menu_id` (`menu_id`),
  CONSTRAINT `fk_right_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_right
-- ----------------------------
INSERT INTO `t_right` VALUES ('1', '/dept/main', '部门管理', '', '1', '1');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键role_id',
  `role_name` varchar(255) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '', '1');
INSERT INTO `t_role` VALUES ('2', '管理员', '', '1');
INSERT INTO `t_role` VALUES ('3', '普通会员', '', '1');
INSERT INTO `t_role` VALUES ('4', '游客', '', '1');

-- ----------------------------
-- Table structure for t_role_right_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_role_right_relation`;
CREATE TABLE `t_role_right_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int(11) NOT NULL COMMENT '角色',
  `right_id` int(11) NOT NULL COMMENT '权限',
  `right_type` varchar(255) NOT NULL COMMENT '权限类型（0:可访问，1:可授权）',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`),
  KEY `fk_role_right_relation_role_id` (`role_id`),
  KEY `fk_role_right_relation_right_id` (`right_id`),
  CONSTRAINT `fk_role_right_relation_right_id` FOREIGN KEY (`right_id`) REFERENCES `t_right` (`right_id`),
  CONSTRAINT `fk_role_right_relation_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_role_right_relation
-- ----------------------------
INSERT INTO `t_role_right_relation` VALUES ('1', '1', '1', 'view', '1');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键user_id',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) NOT NULL COMMENT '账号',
  `password` varchar(45) NOT NULL COMMENT '密码',
  `salt` varchar(45) NOT NULL COMMENT 'md5密码盐',
  `username` varchar(45) NOT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `status` int(11) NOT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`user_id`),
  KEY `fk_user_dept_id` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '1', null, 'admin', '9c9d6357c2e36766', '&|^286._', '刘德华', null, null, '415656544@qq.com', null, '1', '2017-10-18 17:42:40', null, null, null);

-- ----------------------------
-- Table structure for t_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role_relation`;
CREATE TABLE `t_user_role_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户',
  `role_id` int(11) NOT NULL COMMENT '角色',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`),
  KEY `fk_user_role_relation_user_id` (`user_id`),
  KEY `fk_user_role_relation_role_id` (`role_id`),
  CONSTRAINT `fk_user_role_relation_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`),
  CONSTRAINT `fk_user_role_relation_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role_relation
-- ----------------------------
INSERT INTO `t_user_role_relation` VALUES ('1', '1', '1', '1');
