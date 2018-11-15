/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : aooms

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2018-11-15 08:53:32
*/
/*
aooms script
 */

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for aooms_admin_dict
-- ----------------------------
DROP TABLE IF EXISTS `aooms_admin_dict`;
CREATE TABLE `aooms_admin_dict` (
  `id` varchar(32) NOT NULL,
  `parent_dict_id` varchar(32) DEFAULT NULL COMMENT '父ID',
  `dicttype_id` varchar(32) DEFAULT NULL COMMENT '字典类型ID',
  `dict_name` varchar(200) DEFAULT NULL COMMENT '字典名称',
  `dict_code` varchar(255) DEFAULT NULL COMMENT '字典编码',
  `is_default` char(1) DEFAULT NULL COMMENT '是否内置 Y-是 N-否',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `dict_extension` varchar(500) DEFAULT NULL COMMENT '扩展参数  建议 json格式',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_admin_dict
-- ----------------------------
INSERT INTO `aooms_admin_dict` VALUES ('257215353380147200', 'ROOT', '0', '中', null, null, null, 'N', '0', null, '2018-10-11 18:41:59', '2018-10-12 10:49:29');
INSERT INTO `aooms_admin_dict` VALUES ('257215367867273216', 'ROOT', '1', '2', null, null, null, 'Y', '0', null, '2018-10-11 18:42:02', '2018-10-12 10:49:27');
INSERT INTO `aooms_admin_dict` VALUES ('257218744508485632', '257215353380147200', '1', '7', null, null, null, 'Y', '0', null, '2018-10-11 18:55:27', '2018-10-12 10:28:30');
INSERT INTO `aooms_admin_dict` VALUES ('257219364602777600', '257215353380147200', '1', '5', null, null, null, 'Y', '0', null, '2018-10-11 18:57:55', null);
INSERT INTO `aooms_admin_dict` VALUES ('257220547547500544', '257218744508485632', '1', '6', null, null, null, 'Y', '0', null, '2018-10-11 19:02:37', null);
INSERT INTO `aooms_admin_dict` VALUES ('257236146461872128', '257218744508485632', '1', '71', null, null, null, 'Y', '0', null, '2018-10-11 20:04:36', '2018-10-11 20:15:38');
INSERT INTO `aooms_admin_dict` VALUES ('257238440209289216', '257215353380147200', '1', '8', null, null, null, 'Y', '0', null, '2018-10-11 20:13:43', null);
INSERT INTO `aooms_admin_dict` VALUES ('257430521066295296', 'ROOT', '1', '3', null, null, null, 'Y', '0', null, '2018-10-12 08:56:59', null);
INSERT INTO `aooms_admin_dict` VALUES ('257443024399765504', 'ROOT', '0', '123', null, null, null, 'Y', '0', null, '2018-10-12 09:46:40', null);
INSERT INTO `aooms_admin_dict` VALUES ('257443070713270272', 'ROOT', '2', '435', null, null, null, 'Y', '0', null, '2018-10-12 09:46:51', null);
INSERT INTO `aooms_admin_dict` VALUES ('257443089742827520', 'ROOT', '3', '234', null, null, null, 'Y', '0', null, '2018-10-12 09:46:55', null);

-- ----------------------------
-- Table structure for aooms_admin_dicttype
-- ----------------------------
DROP TABLE IF EXISTS `aooms_admin_dicttype`;
CREATE TABLE `aooms_admin_dicttype` (
  `id` varchar(32) NOT NULL,
  `type_name` varchar(200) DEFAULT NULL COMMENT '类型名称',
  `type_code` varchar(255) DEFAULT NULL COMMENT '类型编码',
  `is_default` char(1) DEFAULT NULL COMMENT '系统内置  Y 是 N 否',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_dicttype_code` (`type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_admin_dicttype
-- ----------------------------
INSERT INTO `aooms_admin_dicttype` VALUES ('257215353380147200', '中', null, null, '0', '2018-10-11 18:41:59', '2018-10-12 10:49:29');
INSERT INTO `aooms_admin_dicttype` VALUES ('257215367867273216', '2', null, null, '0', '2018-10-11 18:42:02', '2018-10-12 10:49:27');
INSERT INTO `aooms_admin_dicttype` VALUES ('257218744508485632', '7', null, null, '0', '2018-10-11 18:55:27', '2018-10-12 10:28:30');
INSERT INTO `aooms_admin_dicttype` VALUES ('257219364602777600', '5', null, null, '0', '2018-10-11 18:57:55', null);
INSERT INTO `aooms_admin_dicttype` VALUES ('257220547547500544', '6', null, null, '0', '2018-10-11 19:02:37', null);
INSERT INTO `aooms_admin_dicttype` VALUES ('257236146461872128', '71', null, null, '0', '2018-10-11 20:04:36', '2018-10-11 20:15:38');
INSERT INTO `aooms_admin_dicttype` VALUES ('257238440209289216', '8', null, null, '0', '2018-10-11 20:13:43', null);
INSERT INTO `aooms_admin_dicttype` VALUES ('257430521066295296', '3', null, null, '0', '2018-10-12 08:56:59', null);
INSERT INTO `aooms_admin_dicttype` VALUES ('257443024399765504', '123', null, null, '0', '2018-10-12 09:46:40', null);
INSERT INTO `aooms_admin_dicttype` VALUES ('257443070713270272', '435', null, null, '0', '2018-10-12 09:46:51', null);
INSERT INTO `aooms_admin_dicttype` VALUES ('257443089742827520', '234', null, null, '0', '2018-10-12 09:46:55', null);

-- ----------------------------
-- Table structure for aooms_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `aooms_admin_log`;
CREATE TABLE `aooms_admin_log` (
  `id` varchar(32) NOT NULL,
  `parent_dict_id` varchar(32) DEFAULT NULL COMMENT '父ID',
  `dicttype_id` varchar(32) DEFAULT NULL COMMENT '字典类型ID',
  `dict_name` varchar(200) DEFAULT NULL COMMENT '字典名称',
  `dict_code` varchar(255) DEFAULT NULL COMMENT '字典编码',
  `is_default` char(1) DEFAULT NULL COMMENT '是否内置 Y-是 N-否',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `dict_extension` varchar(500) DEFAULT NULL COMMENT '扩展参数  建议 json格式',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_admin_log
-- ----------------------------
INSERT INTO `aooms_admin_log` VALUES ('257215353380147200', 'ROOT', '0', '中', null, null, null, 'N', '0', null, '2018-10-11 18:41:59', '2018-10-12 10:49:29');
INSERT INTO `aooms_admin_log` VALUES ('257215367867273216', 'ROOT', '1', '2', null, null, null, 'Y', '0', null, '2018-10-11 18:42:02', '2018-10-12 10:49:27');
INSERT INTO `aooms_admin_log` VALUES ('257218744508485632', '257215353380147200', '1', '7', null, null, null, 'Y', '0', null, '2018-10-11 18:55:27', '2018-10-12 10:28:30');
INSERT INTO `aooms_admin_log` VALUES ('257219364602777600', '257215353380147200', '1', '5', null, null, null, 'Y', '0', null, '2018-10-11 18:57:55', null);
INSERT INTO `aooms_admin_log` VALUES ('257220547547500544', '257218744508485632', '1', '6', null, null, null, 'Y', '0', null, '2018-10-11 19:02:37', null);
INSERT INTO `aooms_admin_log` VALUES ('257236146461872128', '257218744508485632', '1', '71', null, null, null, 'Y', '0', null, '2018-10-11 20:04:36', '2018-10-11 20:15:38');
INSERT INTO `aooms_admin_log` VALUES ('257238440209289216', '257215353380147200', '1', '8', null, null, null, 'Y', '0', null, '2018-10-11 20:13:43', null);
INSERT INTO `aooms_admin_log` VALUES ('257430521066295296', 'ROOT', '1', '3', null, null, null, 'Y', '0', null, '2018-10-12 08:56:59', null);
INSERT INTO `aooms_admin_log` VALUES ('257443024399765504', 'ROOT', '0', '123', null, null, null, 'Y', '0', null, '2018-10-12 09:46:40', null);
INSERT INTO `aooms_admin_log` VALUES ('257443070713270272', 'ROOT', '2', '435', null, null, null, 'Y', '0', null, '2018-10-12 09:46:51', null);
INSERT INTO `aooms_admin_log` VALUES ('257443089742827520', 'ROOT', '3', '234', null, null, null, 'Y', '0', null, '2018-10-12 09:46:55', null);

-- ----------------------------
-- Table structure for aooms_rbac_menu
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_menu`;
CREATE TABLE `aooms_rbac_menu` (
  `id` varchar(32) NOT NULL,
  `parent_menu_id` varchar(32) DEFAULT NULL COMMENT '父模块ID',
  `resource_id` varchar(32) DEFAULT NULL COMMENT '资源ID',
  `menu_name` varchar(200) DEFAULT NULL COMMENT '菜单名称',
  `menu_type` char(1) DEFAULT NULL COMMENT '菜单类型 0-目录 1-菜单',
  `open_type` char(1) DEFAULT NULL COMMENT '打开方式 0-默认 1-iframe 2-新窗口',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_menu
-- ----------------------------
INSERT INTO `aooms_rbac_menu` VALUES ('257215353380147200', 'ROOT', null, '中', '0', '0', null, null, 'N', '0', '2018-10-11 18:41:59', '2018-10-12 10:49:29');
INSERT INTO `aooms_rbac_menu` VALUES ('257215367867273216', 'ROOT', null, '2', '1', '0', null, null, 'Y', '0', '2018-10-11 18:42:02', '2018-10-12 10:49:27');
INSERT INTO `aooms_rbac_menu` VALUES ('257218744508485632', '257215353380147200', null, '7', '1', '0', null, null, 'Y', '0', '2018-10-11 18:55:27', '2018-10-12 10:28:30');
INSERT INTO `aooms_rbac_menu` VALUES ('257219364602777600', '257215353380147200', null, '5', '1', '0', null, null, 'Y', '0', '2018-10-11 18:57:55', null);
INSERT INTO `aooms_rbac_menu` VALUES ('257220547547500544', '257218744508485632', null, '6', '1', '0', null, null, 'Y', '0', '2018-10-11 19:02:37', null);
INSERT INTO `aooms_rbac_menu` VALUES ('257236146461872128', '257218744508485632', null, '71', '1', '0', null, null, 'Y', '0', '2018-10-11 20:04:36', '2018-10-11 20:15:38');
INSERT INTO `aooms_rbac_menu` VALUES ('257238440209289216', '257215353380147200', null, '8', '1', '0', null, null, 'Y', '0', '2018-10-11 20:13:43', null);
INSERT INTO `aooms_rbac_menu` VALUES ('257430521066295296', 'ROOT', null, '3', '1', '0', null, null, 'Y', '0', '2018-10-12 08:56:59', null);
INSERT INTO `aooms_rbac_menu` VALUES ('257443024399765504', 'ROOT', null, '123', '0', '0', null, null, 'Y', '0', '2018-10-12 09:46:40', null);
INSERT INTO `aooms_rbac_menu` VALUES ('257443070713270272', 'ROOT', null, '435', '2', '0', null, null, 'Y', '0', '2018-10-12 09:46:51', null);
INSERT INTO `aooms_rbac_menu` VALUES ('257443089742827520', 'ROOT', null, '234', '3', '0', null, null, 'Y', '0', '2018-10-12 09:46:55', null);

-- ----------------------------
-- Table structure for aooms_rbac_org
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_org`;
CREATE TABLE `aooms_rbac_org` (
  `id` varchar(32) NOT NULL,
  `parent_org_id` varchar(32) DEFAULT NULL COMMENT '父机构ID',
  `org_name` varchar(200) DEFAULT NULL COMMENT '机构名称',
  `org_shortname` varchar(100) DEFAULT NULL COMMENT '机构简称',
  `org_code` varchar(255) DEFAULT NULL COMMENT '机构编码',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `org_level` int(1) DEFAULT NULL COMMENT '树层级',
  `org_permission` varchar(100) DEFAULT NULL COMMENT '机构权限编码',
  `data_permission` varchar(255) DEFAULT NULL COMMENT '数据权限标识代码  格式：机构.机构1.操作人ID',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_org
-- ----------------------------
INSERT INTO `aooms_rbac_org` VALUES ('262216505125507072', 'ROOT', '1', null, '1', null, null, 'Y', '1', '1', '0001', '0001', null, '2018-10-25 13:54:46', null);
INSERT INTO `aooms_rbac_org` VALUES ('262216516278161408', 'ROOT', '2', null, '2', null, null, 'Y', '113', '1', '0003', '0003', null, '2018-10-25 13:54:49', '2018-11-01 10:59:31');
INSERT INTO `aooms_rbac_org` VALUES ('262216543364976640', '262216505125507072', '12', null, '12', null, null, 'Y', '1', '2', '0001', '0001.0001', null, '2018-10-25 13:54:55', '2018-11-01 12:46:50');
INSERT INTO `aooms_rbac_org` VALUES ('262216789121830912', '262216516278161408', '21', null, '21', null, null, 'Y', '1', '2', '0001', '0003.0001', null, '2018-10-25 13:55:54', null);
INSERT INTO `aooms_rbac_org` VALUES ('262216838321016832', '262216789121830912', '22', null, '22', null, null, 'Y', '1', '3', '0001', '0003.0001.0001', null, '2018-10-25 13:56:06', null);
INSERT INTO `aooms_rbac_org` VALUES ('262224019124654080', 'ROOT', '340', null, '3', null, null, 'Y', '11', '1', '0002', '0002', null, '2018-10-25 14:24:38', '2018-11-01 15:09:55');
INSERT INTO `aooms_rbac_org` VALUES ('ROOT', '', '顶层机构', '顶层机构', 'ROOT', null, null, 'Y', '0', '0', '', '', null, null, null);

-- ----------------------------
-- Table structure for aooms_rbac_permission
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_permission`;
CREATE TABLE `aooms_rbac_permission` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `is_halfselect` char(1) DEFAULT NULL COMMENT '半选中状态 Y-是  N-否',
  `create_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_permission
-- ----------------------------
INSERT INTO `aooms_rbac_permission` VALUES ('268484257125502976', '267321253608558592', '266986776843784192', 'N', '2018-11-11 21:00:35');
INSERT INTO `aooms_rbac_permission` VALUES ('268484257150668800', '267321253608558592', '266978270006743040', 'Y', '2018-11-11 21:00:35');
INSERT INTO `aooms_rbac_permission` VALUES ('269153043788861440', '267323584362319872', '266979132951236608', 'N', '2018-11-13 17:18:06');
INSERT INTO `aooms_rbac_permission` VALUES ('269153044539641856', '267323584362319872', '266978270006743040', 'Y', '2018-11-13 17:18:06');
INSERT INTO `aooms_rbac_permission` VALUES ('269153141126074368', '267326095118831616', '266979132951236608', 'N', '2018-11-13 17:18:29');
INSERT INTO `aooms_rbac_permission` VALUES ('269153141130268672', '267326095118831616', '266986727237750784', 'N', '2018-11-13 17:18:29');
INSERT INTO `aooms_rbac_permission` VALUES ('269153141163823104', '267326095118831616', '266978270006743040', 'Y', '2018-11-13 17:18:29');

-- ----------------------------
-- Table structure for aooms_rbac_resource
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_resource`;
CREATE TABLE `aooms_rbac_resource` (
  `id` varchar(32) NOT NULL,
  `parent_resource_id` varchar(32) DEFAULT NULL COMMENT '父模块ID',
  `resource_name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `resource_code` varchar(255) DEFAULT NULL COMMENT '资源编码',
  `resource_type` char(1) DEFAULT NULL COMMENT '资源类型 0-目录 1-模块 2-按钮 3-接口',
  `resource_url` varchar(255) DEFAULT NULL COMMENT '资源链接地址',
  `open_type` char(1) DEFAULT NULL COMMENT '打开方式 0-默认 1-iframe 2-新窗口',
  `permission` varchar(255) DEFAULT NULL COMMENT '资源权限标识',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_resource
-- ----------------------------
INSERT INTO `aooms_rbac_resource` VALUES ('266978270006743040', 'ROOT', '系统管理', 'system', '0', '/rbac', '0', null, 'gear', null, 'Y', '1', '2018-11-07 17:16:19', '2018-11-08 11:45:07');
INSERT INTO `aooms_rbac_resource` VALUES ('266979132951236608', '266978270006743040', '机构管理', 'org', '1', '/rbac/org', '0', null, 'cubes', null, 'Y', '1', '2018-11-07 17:19:45', '2018-11-08 18:08:15');
INSERT INTO `aooms_rbac_resource` VALUES ('266979208247382016', '266978270006743040', '用户管理', 'user', '1', '/rbac/user', '0', null, 'user', null, 'Y', '2', '2018-11-07 17:20:03', '2018-11-08 15:19:31');
INSERT INTO `aooms_rbac_resource` VALUES ('266986727237750784', '266978270006743040', '资源管理', 'resource', '1', '/rbac/resource', '0', null, 'delicious', null, 'Y', '5', '2018-11-07 17:49:56', '2018-11-07 17:50:16');
INSERT INTO `aooms_rbac_resource` VALUES ('266986776843784192', '266978270006743040', '角色管理', 'role', '1', '/rbac/role', '0', null, 'user-circle', null, 'Y', '4', '2018-11-07 17:50:07', '2018-11-08 15:19:52');
INSERT INTO `aooms_rbac_resource` VALUES ('ROOT', null, '顶层', 'ROOT', '0', null, null, null, null, null, 'Y', '0', '2018-10-23 17:38:01', '2018-10-23 17:38:01');

-- ----------------------------
-- Table structure for aooms_rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_role`;
CREATE TABLE `aooms_rbac_role` (
  `id` varchar(32) NOT NULL,
  `org_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `is_admin` char(1) DEFAULT NULL COMMENT '是否管理员角色  Y-是 N-否 ',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_role
-- ----------------------------
INSERT INTO `aooms_rbac_role` VALUES ('267321253608558592', 'ROOT', '管理员', 'system', null, 'Y', '0', 'Y', '2018-11-08 15:59:13', '2018-11-08 16:01:47');
INSERT INTO `aooms_rbac_role` VALUES ('267323584362319872', '262216516278161408', '角色1', '1', '1', 'Y', '1', 'N', '2018-11-08 16:08:29', '2018-11-08 16:10:36');
INSERT INTO `aooms_rbac_role` VALUES ('267326095118831616', '262216505125507072', '角色2', '2', '2', 'Y', '1', 'N', '2018-11-08 16:18:27', '2018-11-08 17:24:42');

-- ----------------------------
-- Table structure for aooms_rbac_tenant
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_tenant`;
CREATE TABLE `aooms_rbac_tenant` (
  `id` varchar(32) NOT NULL,
  `parent_org_id` varchar(32) DEFAULT NULL COMMENT '父机构ID',
  `org_name` varchar(200) DEFAULT NULL COMMENT '机构名称',
  `org_shortname` varchar(100) DEFAULT NULL COMMENT '机构简称',
  `org_code` varchar(255) DEFAULT NULL COMMENT '机构编码',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `tree_level` int(1) DEFAULT NULL COMMENT '树层级',
  `tree_code` varchar(100) DEFAULT NULL COMMENT '树快速查询编码',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_tenant
-- ----------------------------
INSERT INTO `aooms_rbac_tenant` VALUES ('252117563901743104', 'ROOT', '第11', null, '7', null, '6', 'Y', '7', null, null, null, '2018-10-10 12:21:43');
INSERT INTO `aooms_rbac_tenant` VALUES ('252117989950754816', 'ROOT', '第2', null, '8', null, null, 'Y', '8', null, null, null, '2018-10-10 12:20:28');
INSERT INTO `aooms_rbac_tenant` VALUES ('252118060553474048', 'ROOT', '第3', null, '9', null, null, 'Y', '9', null, null, null, '2018-10-10 12:20:29');
INSERT INTO `aooms_rbac_tenant` VALUES ('252118128241152000', 'ROOT', '第4', null, '10', null, null, 'Y', '10', null, null, null, '2018-10-10 12:20:29');
INSERT INTO `aooms_rbac_tenant` VALUES ('252119922958667776', 'ROOT', '第51', '999', '00', null, null, 'Y', '11', null, null, null, '2018-10-11 13:02:14');
INSERT INTO `aooms_rbac_tenant` VALUES ('252122146900283392', 'ROOT', '第6', null, null, null, '8', 'Y', null, null, null, null, null);
INSERT INTO `aooms_rbac_tenant` VALUES ('252123287105048576', 'ROOT', '第7', null, null, null, '9', 'Y', null, null, null, null, null);
INSERT INTO `aooms_rbac_tenant` VALUES ('252123581616492544', 'ROOT', '第8', null, null, null, '9', 'Y', null, null, null, null, null);
INSERT INTO `aooms_rbac_tenant` VALUES ('252124272418361344', 'ROOT', '第9', null, null, null, '9', 'Y', null, null, null, null, null);
INSERT INTO `aooms_rbac_tenant` VALUES ('252124411157549056', 'ROOT', '第10', null, null, null, '9', 'Y', null, null, null, '2018-09-27 17:32:24', null);
INSERT INTO `aooms_rbac_tenant` VALUES ('256075948120608768', 'ROOT', '第11', '131', '322', null, '23123', 'Y', '0', null, null, '2018-10-08 15:14:23', null);
INSERT INTO `aooms_rbac_tenant` VALUES ('256078934423113728', 'ROOT', '12388', '123', '123', null, '123', 'Y', '123', null, null, '2018-10-08 15:26:15', '2018-10-09 08:58:39');
INSERT INTO `aooms_rbac_tenant` VALUES ('256078973149122560', 'ROOT', '123', '232', '23', null, '323', 'Y', '0', null, null, '2018-10-08 15:26:24', null);
INSERT INTO `aooms_rbac_tenant` VALUES ('256446263040413696', 'ROOT', '999', '999', '999', null, '999', 'Y', '0', null, null, '2018-10-09 15:45:53', null);
INSERT INTO `aooms_rbac_tenant` VALUES ('256743759763476480', '252117563901743104', '56', '123', '123', null, '123123', 'Y', '0', null, null, '2018-10-10 11:28:02', '2018-10-12 08:50:53');
INSERT INTO `aooms_rbac_tenant` VALUES ('256744140635639808', '252117563901743104', '34', '23423', '234', null, '4243', 'Y', '0', null, null, '2018-10-10 11:29:33', '2018-10-10 11:29:48');
INSERT INTO `aooms_rbac_tenant` VALUES ('257457467774996480', '252117563901743104', '666', 're', '666', null, null, 'Y', '0', null, null, '2018-10-12 10:44:03', '2018-10-12 10:44:08');

-- ----------------------------
-- Table structure for aooms_rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_user`;
CREATE TABLE `aooms_rbac_user` (
  `id` varchar(32) NOT NULL,
  `org_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `user_nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `account` varchar(20) DEFAULT NULL COMMENT '登陆账号',
  `password` varchar(255) DEFAULT NULL COMMENT '登陆密码',
  `sex` char(1) DEFAULT NULL COMMENT '性别 0-男 1-女',
  `is_admin` char(1) DEFAULT NULL COMMENT '管理员  Y-是 N-否',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '状态  Y-启用 N-禁用',
  `ordinal` int(5) DEFAULT NULL COMMENT '序号',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_user
-- ----------------------------
INSERT INTO `aooms_rbac_user` VALUES ('10', null, '李四四8', '消失的风筝', 'admin1', '1', '1', null, '13345678900', null, '1234567890@qq.com', '没有备注', 'N', null, '2018-01-01 14:00:09', '2018-03-07 16:23:55');
INSERT INTO `aooms_rbac_user` VALUES ('123', null, '张三三1', '消失的风筝', 'admin2', '1', '0', null, '13345678900', null, '1234567890@qq.com', '这个备注可能会很长很长的这个备注可能会很长很长的这个备注可能会很长很长的这个备注可能会很长很长的', 'Y', null, '2018-01-01 14:00:09', '2018-10-08 19:20:12');
INSERT INTO `aooms_rbac_user` VALUES ('252112545710608384', null, null, null, null, null, null, null, null, null, null, null, 'Y', null, null, null);
INSERT INTO `aooms_rbac_user` VALUES ('252113117272608768', null, null, null, null, null, null, null, null, null, null, null, 'Y', '1', null, null);
INSERT INTO `aooms_rbac_user` VALUES ('252116958130999296', null, '6', null, '61', '6', '0', null, '6', null, null, '6', 'Y', '1', null, null);
INSERT INTO `aooms_rbac_user` VALUES ('252117563901743104', 'ROOT', '6', null, '67', '6', '0', null, '6', null, null, '6', 'N', '1', null, '2018-10-24 13:57:55');
INSERT INTO `aooms_rbac_user` VALUES ('252117989950754816', 'ROOT', '张三', null, 'test', '1027:b0bb306048f7b19b4ce82b5b70396d1b0650b9bf8c282535:c1f709b71a4de3bad1588b59c0cdbc0aef2b6d8743075e62', null, null, null, null, null, null, 'Y', '1', null, '2018-11-13 17:18:17');
INSERT INTO `aooms_rbac_user` VALUES ('252118060553474048', 'ROOT', null, null, null, null, null, null, null, null, null, null, 'N', '1', null, '2018-11-07 15:54:24');
INSERT INTO `aooms_rbac_user` VALUES ('252118128241152000', 'ROOT', null, null, null, null, null, null, null, null, null, null, 'Y', '1', null, '2018-10-24 13:57:45');
INSERT INTO `aooms_rbac_user` VALUES ('252119922958667776', 'ROOT', null, null, null, null, null, null, null, null, null, null, 'Y', '1', null, '2018-10-24 13:57:46');
INSERT INTO `aooms_rbac_user` VALUES ('252122146900283392', 'ROOT', '8', null, '83', '8', '0', null, '8', null, null, '8', 'Y', '1', null, '2018-10-24 13:57:47');
INSERT INTO `aooms_rbac_user` VALUES ('252123287105048576', 'ROOT', '9', null, '91', '9', '0', null, '9', null, null, '9', 'Y', '1', null, '2018-10-24 13:57:47');
INSERT INTO `aooms_rbac_user` VALUES ('252123581616492544', 'ROOT', '9', null, '92', '9', '0', null, '9', null, null, '9', 'Y', '1', null, null);
INSERT INTO `aooms_rbac_user` VALUES ('252124272418361344', 'ROOT', '9', null, '93', '9', '0', null, '9', null, null, '9', 'Y', '1', null, null);
INSERT INTO `aooms_rbac_user` VALUES ('252124411157549056', 'ROOT', '9', null, '94', '9', '0', null, '9', null, null, '9', 'Y', '1', '2018-09-27 17:32:24', null);
INSERT INTO `aooms_rbac_user` VALUES ('261069476450013184', '252117563901743104', 'w', null, 'ere', 'ee', null, null, null, null, null, null, 'Y', '1', '2018-10-22 09:56:53', null);
INSERT INTO `aooms_rbac_user` VALUES ('261495438773850112', '257457467774996480', '23', null, '23', '23', '0', null, null, null, null, null, 'Y', '1', '2018-10-23 14:09:30', null);
INSERT INTO `aooms_rbac_user` VALUES ('261495521640714240', '252118060553474048', '23', null, '3', '23', '0', null, null, null, null, null, 'Y', '1', '2018-10-23 14:09:50', '2018-10-23 14:41:48');
INSERT INTO `aooms_rbac_user` VALUES ('262272308003999744', '262216838321016832', 'www', null, '2222', null, '0', null, null, null, null, null, 'N', '1', '2018-10-25 17:36:31', '2018-11-07 15:54:33');
INSERT INTO `aooms_rbac_user` VALUES ('267342332607598592', 'ROOT', '6', null, '66', '1027:66ffab1ce9c49aebd75fac25fd3751ba67a5243c53526bd8:7f8298a04ef30fe69b626b8ec994286ec4142bf183c5fd06', '0', 'N', null, null, null, null, 'Y', '1', '2018-11-08 17:22:59', null);
INSERT INTO `aooms_rbac_user` VALUES ('5', null, '李四四3789', '消失的风筝', 'admin7', '1', '1', null, '13345678900', null, '1234567890@qq.com', '没有备注', 'N', '1', '2018-01-01 14:00:09', '2018-09-27 19:29:04');
INSERT INTO `aooms_rbac_user` VALUES ('6', null, '李四四4', '消失的风筝', 'admin8', '1', '1', null, '13345678900', null, '1234567890@qq.com', '没有备注', 'N', '1', '2018-01-01 14:00:09', '2018-03-07 16:23:55');
INSERT INTO `aooms_rbac_user` VALUES ('7', null, '李四四5', '消失的风筝', 'admin9', '1', '1', null, '13345678900', null, '1234567890@qq.com', '没有备注', 'N', '1', '2018-01-01 14:00:09', '2018-03-07 16:23:55');
INSERT INTO `aooms_rbac_user` VALUES ('8', null, '李四四6', '消失的风筝', 'admin0', '1', '1', null, '13345678900', null, '1234567890@qq.com', '没有备注', 'N', '1', '2018-01-01 14:00:09', '2018-03-07 16:23:55');
INSERT INTO `aooms_rbac_user` VALUES ('9', null, '李四四7', '消失的风筝', 'admin00', '1', '1', null, '13345678900', null, '1234567890@qq.com', '没有备注', 'N', '1', '2018-01-01 14:00:09', '2018-03-07 16:23:55');
INSERT INTO `aooms_rbac_user` VALUES ('admin', 'ROOT', '超级管理员', '超级管理员', 'admin', '1027:eb9e8434db52d1f11db342858787a3c075dccc7f6a65cc9e:197df28343ce3a9e292d6f5a912ab10cb7517a9a2bb8662a', '1', 'Y', '6667', null, '1234567890@qq.com', '', 'Y', '0', '2018-01-01 14:00:09', '2018-11-14 18:18:12');

-- ----------------------------
-- Table structure for aooms_rbac_userrole
-- ----------------------------
DROP TABLE IF EXISTS `aooms_rbac_userrole`;
CREATE TABLE `aooms_rbac_userrole` (
  `id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aooms_rbac_userrole
-- ----------------------------
INSERT INTO `aooms_rbac_userrole` VALUES ('267342333048000512', '267342332607598592', '267326095118831616', '2018-11-08 17:22:59');
INSERT INTO `aooms_rbac_userrole` VALUES ('269153091083833344', '252117989950754816', '267323584362319872', '2018-11-13 17:18:17');
INSERT INTO `aooms_rbac_userrole` VALUES ('269153091100610560', '252117989950754816', '267326095118831616', '2018-11-13 17:18:17');
