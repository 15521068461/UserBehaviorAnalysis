/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : atm2

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2020-09-01 11:35:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_data_label
-- ----------------------------
DROP TABLE IF EXISTS `t_data_label`;
CREATE TABLE `t_data_label` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LABEL_NAME` varchar(200) NOT NULL COMMENT '��ǩ��',
  `LABEL_EXPLAIN` varchar(2000) DEFAULT NULL COMMENT '˵��',
  `DATA_STATUS` tinyint(4) DEFAULT '1' COMMENT '����״̬:0-ɾ�� 1-����',
  `DATA_CREATE_AT` datetime NOT NULL COMMENT '����ʱ��',
  `DATA_CREATE_USER_ID` bigint(20) NOT NULL COMMENT '�����˱��',
  `DATA_UPDATE_AT` datetime NOT NULL COMMENT '������ʱ��',
  `DATA_UPDATE_USER_ID` bigint(20) NOT NULL COMMENT '�������˱��',
  `EXT_FILED` varchar(2000) DEFAULT NULL COMMENT '��չ�ֶ�',
  PRIMARY KEY (`ID`,`LABEL_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='����-�ɼ���ǩ��';

-- ----------------------------
-- Records of t_data_label
-- ----------------------------
INSERT INTO `t_data_label` VALUES ('66', '登录', '111', null, '2020-08-16 22:08:08', '1', '2020-08-16 22:08:08', '1', 'null');
INSERT INTO `t_data_label` VALUES ('67', 'A产品浏览', '111', null, '2020-08-16 22:08:38', '1', '2020-08-16 22:08:38', '1', 'null');

-- ----------------------------
-- Table structure for t_data_record
-- ----------------------------
DROP TABLE IF EXISTS `t_data_record`;
CREATE TABLE `t_data_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RECORD_IP` varchar(400) DEFAULT NULL COMMENT '�û�IP',
  `RECORD_USER_ID` varchar(400) DEFAULT NULL COMMENT '�û�ID',
  `RECORD_LABELS` varchar(400) DEFAULT NULL COMMENT '�¼���ǩ�����ŷָ�',
  `DATA_DETAILS` varchar(6000) DEFAULT NULL COMMENT '�¼���ע��JSON�ṹ',
  `DATA_STATUS` tinyint(4) DEFAULT '1' COMMENT '����״̬:0-ɾ�� 1-����',
  `DATA_CREATE_AT` datetime NOT NULL COMMENT '����ʱ��',
  `DATA_CREATE_USER_ID` bigint(20) DEFAULT NULL COMMENT '�����˱��',
  `DATA_UPDATE_AT` datetime DEFAULT NULL COMMENT '������ʱ��',
  `DATA_UPDATE_USER_ID` bigint(20) DEFAULT NULL COMMENT '�������˱��',
  `EXT_FILED` varchar(2000) DEFAULT NULL COMMENT '��չ�ֶ�',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='����-�ɼ���¼��';

-- ----------------------------
-- Records of t_data_record
-- ----------------------------
INSERT INTO `t_data_record` VALUES ('1', '192.168.1.2', '01', '登录', '这是一个登录标签', '1', '2020-08-07 20:23:12', null, null, null, 'null');
INSERT INTO `t_data_record` VALUES ('2', '192.168.1.2', '01', '登录', '这是一个登录标签', '1', '2020-08-08 16:30:28', null, null, null, null);
INSERT INTO `t_data_record` VALUES ('3', '192.168.1.2', '01', '登录', '这是一个登录标签', '1', '2020-08-09 17:07:23', null, null, null, null);
INSERT INTO `t_data_record` VALUES ('4', '192.168.1.2', '01', '登录', '这是一个登录标签', '1', '2020-08-09 22:10:44', null, null, null, null);
INSERT INTO `t_data_record` VALUES ('5', '192.168.1.2', '02', '登录', '这是一个登录标签', '1', '2020-08-09 22:12:18', null, null, null, null);
INSERT INTO `t_data_record` VALUES ('6', '192.168.1.2', '01', 'A产品浏览', '这是一个A产品浏览标签', '1', '2020-08-16 22:13:09', null, null, null, null);
INSERT INTO `t_data_record` VALUES ('7', '192.168.1.2', '01', '登录', '这是一个登录标签', '1', '2020-08-16 14:45:18', null, null, null, null);
INSERT INTO `t_data_record` VALUES ('8', '192.168.1.2', '01', '登录', '这是一个登录标签', '1', '2020-08-16 14:46:03', null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_cache
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_cache`;
CREATE TABLE `t_sys_cache` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CACHE_KEY` varchar(200) DEFAULT NULL COMMENT '��',
  `CACHE_VALUE` varchar(2000) DEFAULT NULL COMMENT 'ֵ',
  `DATA_STATUS` tinyint(4) DEFAULT '1' COMMENT '����״̬:0-ɾ�� 1-����',
  `DATA_CREATE_AT` datetime DEFAULT NULL COMMENT '����ʱ��',
  `DATA_CREATE_USER_ID` bigint(20) DEFAULT NULL COMMENT '�����˱��',
  `DATA_UPDATE_AT` datetime DEFAULT NULL COMMENT '������ʱ��',
  `DATA_UPDATE_USER_ID` bigint(20) DEFAULT NULL COMMENT '�������˱��',
  `EXT_FILED` varchar(2000) DEFAULT NULL COMMENT '��չ�ֶ�',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='ϵͳ-������Ϣ��';

-- ----------------------------
-- Records of t_sys_cache
-- ----------------------------
INSERT INTO `t_sys_cache` VALUES ('30', '1598845221448', '1', null, null, null, null, null, 'null');

-- ----------------------------
-- Table structure for t_user_base
-- ----------------------------
DROP TABLE IF EXISTS `t_user_base`;
CREATE TABLE `t_user_base` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_MOBILE` varchar(200) DEFAULT NULL COMMENT '�ֻ���',
  `USER_PASSWORD` varchar(200) DEFAULT NULL COMMENT '����',
  `USER_NAME` varchar(200) DEFAULT NULL COMMENT '����',
  `USER_SEX` tinyint(4) DEFAULT '0' COMMENT '�Ա�0-Ů��1-��',
  `USER_LOGO` varchar(1000) DEFAULT NULL COMMENT 'ͷ',
  `USER_INTRO` varchar(1000) DEFAULT NULL COMMENT '���',
  `DATA_STATUS` tinyint(4) DEFAULT '1' COMMENT '����״̬:0-ɾ�� 1-����',
  `DATA_CREATE_AT` datetime NOT NULL COMMENT '����ʱ��',
  `DATA_CREATE_USER_ID` bigint(20) DEFAULT NULL COMMENT '�����˱��',
  `DATA_UPDATE_AT` datetime DEFAULT NULL COMMENT '������ʱ��',
  `DATA_UPDATE_USER_ID` bigint(20) DEFAULT NULL COMMENT '�������˱��',
  `EXT_FILED` varchar(2000) DEFAULT NULL COMMENT '��չ�ֶ�',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='�û�-������Ϣ��';

-- ----------------------------
-- Records of t_user_base
-- ----------------------------
INSERT INTO `t_user_base` VALUES ('1', '155210', '123', '张三AB', '2', 'D:eclipse.metadata.pluginsorg.eclipse.wst.server.core	mp1wtpwebappsmyUserDo2\\upload微信图片_20200620231535.jpg', '本科毕业', '1', '2020-08-01 10:57:26', '1', '2020-08-21 21:15:23', '1', 'null');
INSERT INTO `t_user_base` VALUES ('2', '155212', '321', '李四', '1', '头像2', '专科毕业', '1', '2020-08-12 15:22:16', '2', '2020-08-12 15:22:22', null, null);
