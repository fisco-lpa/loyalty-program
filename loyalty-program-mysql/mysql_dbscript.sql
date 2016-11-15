/*
Navicat MySQL Data Transfer

Source Server         : 192.168.103.35
Source Server Version : 50716
Source Host           : 192.168.103.35:3306
Source Database       : points

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2016-11-15 14:02:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户ID',
  `user_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户所属用户ID',
  `account_balance` int(11) NOT NULL COMMENT '账户积分剩余数量',
  `account_type_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户类型ID',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '修改人',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='账户表';

-- ----------------------------
-- Table structure for account_type
-- ----------------------------
DROP TABLE IF EXISTS `account_type`;
CREATE TABLE `account_type` (
  `account_type_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户类型ID',
  `describe` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户类型说明',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '修改人',
  PRIMARY KEY (`account_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='账户类型表';

-- ----------------------------
-- Table structure for configure_category
-- ----------------------------
DROP TABLE IF EXISTS `configure_category`;
CREATE TABLE `configure_category` (
  `category_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户类型ID',
  `category_name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户类型说明',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '修改人',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='配置信息类别表';

-- ----------------------------
-- Table structure for configure_detail
-- ----------------------------
DROP TABLE IF EXISTS `configure_detail`;
CREATE TABLE `configure_detail` (
  `detail_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '类别明细ID',
  `detail_name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '类别明细名称',
  `category_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '类别ID',
  `describe` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '修改人',
  PRIMARY KEY (`detail_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='配置信息明细表';

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `countryname` varchar(255) DEFAULT NULL COMMENT '名称',
  `countrycode` varchar(255) DEFAULT NULL COMMENT '代码',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8 COMMENT='国家信息';

-- ----------------------------
-- Table structure for points_transation
-- ----------------------------
DROP TABLE IF EXISTS `points_transation`;
CREATE TABLE `points_transation` (
  `trans_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '积分交易ID',
  `roll_out_account` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '转出账户',
  `roll_in_account` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '转入账户',
  `trans_amount` int(11) NOT NULL COMMENT '交易积分数量',
  `describe` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transfer_time` datetime NOT NULL COMMENT '交易时间',
  `transfer_type` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '交易类型 授信，发放',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '修改人',
  PRIMARY KEY (`trans_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='积分交易表';

-- ----------------------------
-- Table structure for points_transation_detail
-- ----------------------------
DROP TABLE IF EXISTS `points_transation_detail`;
CREATE TABLE `points_transation_detail` (
  `detail_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '逐笔明细流水号',
  `source_detail_id` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '来源流水号',
  `trans_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '积分交易ID',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `ext_ref` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '外部引用',
  `status` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态  0.冻结,1.正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '修改人',
  `roll_out_account` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '转出账户',
  `roll_in_account` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '转入账户',
  `trans_amount` int(11) NOT NULL COMMENT '交易积分数量',
  `cur_balance` int(11) NOT NULL COMMENT '当笔积分剩余数量',
  `credit_party` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '授信方账户',
  `merchant` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '商户账户',
  `credit_create_time` datetime NOT NULL COMMENT '授信创建时间',
  `transfer_time` datetime NOT NULL COMMENT '交易时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='积分交易逐笔明细表';

-- ----------------------------
-- Table structure for points_user
-- ----------------------------
DROP TABLE IF EXISTS `points_user`;
CREATE TABLE `points_user` (
  `user_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID',
  `user_name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名称',
  `user_password` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户密码',
  `phone_number` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '手机号',
  `user_type` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户类型 0.授信，1.商户,2.用户',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户表';
SET FOREIGN_KEY_CHECKS=1;
