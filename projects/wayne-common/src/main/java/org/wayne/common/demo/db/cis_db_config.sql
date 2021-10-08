/*
Navicat MySQL Data Transfer

Source Server         : 88888888888888888888888888888888
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : pub

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2021-05-11 10:47:08
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cis_db_config
-- ----------------------------
DROP TABLE IF EXISTS `cis_db_config`;
CREATE TABLE `cis_db_config`
(
    `id`       int          NOT NULL AUTO_INCREMENT,
    `dbId`     varchar(30)  NOT NULL COMMENT '数据库标识',
    `url`      varchar(100) NOT NULL COMMENT '数据库地址',
    `username` varchar(50)  NOT NULL COMMENT '用户名',
    `password` varchar(50)  NOT NULL COMMENT '密码',
    `document` varchar(500) NOT NULL COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 70
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of cis_db_config
-- ----------------------------
INSERT INTO `cis_db_config`
VALUES ('14', 'master', 'jdbc:mysql://172.16.42.148:3306/pub', 'pubusr', 'pubusr', '数据仓库');
INSERT INTO `cis_db_config`
VALUES ('15', 'QHWD', 'jdbc:mysql://172.16.42.147:3306/ods', 'odsusr', 'odsusr', '青海网贷大屏统计');
INSERT INTO `cis_db_config`
VALUES ('45', 'inner_log_dbid', 'jdbc:mysql://172.16.42.147:3306/pub', 'pubusr', 'pubusr', '青海lt公共库');
INSERT INTO `cis_db_config`
VALUES ('50', 'qhfd-trade', 'jdbc:mysql://172.16.42.145:3306/A060000206', 'ccbcusr', 'ccbcusr', '青海飞贷交易库');
INSERT INTO `cis_db_config`
VALUES ('63', 'qh-jzfq', 'jdbc:mysql://172.16.42.119:3306/a060000211', 'ccbcusr', 'ccbcusr_qhwd', '青海桔子分期');
INSERT INTO `cis_db_config`
VALUES ('65', 'qh-360jq', 'jdbc:mysql://172.16.42.119:3306/a060000212', 'ccbcusr', 'ccbcusr_qhwd', '青海360借钱');
