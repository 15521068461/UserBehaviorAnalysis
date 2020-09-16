DROP DATABASE IF EXISTS demo_yunyin;
CREATE DATABASE demo_yunyin;
USE demo_yunyin;

-- ----------------------------
-- Table structure for t_ad
-- ----------------------------
DROP TABLE IF EXISTS `t_ad`;
CREATE TABLE `t_ad` (
  `ID` bigint(20) NOT NULL COMMENT '编号：雪花算法ID',
  `AD_FLAG` varchar(100) NOT NULL COMMENT '广告标签：区分广告位',
  `AD_LINK` varchar(500) DEFAULT NULL COMMENT '广告链接：跳转URL/NULL-无需跳转',
  `AD_PIC` varchar(500) NOT NULL COMMENT '广告图片：显示图片',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告表';

-- ----------------------------
-- Records of t_ad
-- ----------------------------


-- ----------------------------
-- Table structure for t_doc_print
-- ----------------------------
DROP TABLE IF EXISTS `t_doc_print`;
CREATE TABLE `t_doc_print` (
  `ID` bigint(20) NOT NULL COMMENT '文档编号：雪花算法ID',
  `USER_ID` bigint(20) NOT NULL COMMENT '用户编号',
  `DOC_NAME` varchar(500) NOT NULL COMMENT '文档名称',
  `DOC_PAGE` int(11) NOT NULL COMMENT '文档页数',
  `DOC_FX` int(1) NOT NULL COMMENT '文档方向：1-纵向、2-横向',
  `DOC_PATH` varchar(500) NOT NULL COMMENT '文档路径：HTTP绝对路径',
  `PRINT_COLOR` int(1) NOT NULL COMMENT '打印颜色：1-黑白、2-彩色',
  `PRINT_DSM` int(1) NOT NULL COMMENT '打印单双面：1-单面、2-双面',
  `PRINT_START` int(11) NOT NULL COMMENT '打印范围起始',
  `PRINT_END` int(11) NOT NULL COMMENT '打印范围结束',
  `PRINT_COPIES` int(11) NOT NULL COMMENT '打印份数',
  `PRINT_STATUS` int(1) NOT NULL COMMENT '打印状态：1-未打印、2-打印中、3-打印结束',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档打印表';

-- ----------------------------
-- Table structure for t_session
-- ----------------------------
DROP TABLE IF EXISTS `t_session`;
CREATE TABLE `t_session` (
  `ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SESSION缓存表';

-- ----------------------------
-- Table structure for t_sms
-- ----------------------------
DROP TABLE IF EXISTS `t_sms`;
CREATE TABLE `t_sms` (
  `ID` bigint(20) NOT NULL,
  `CODE` bigint(20) DEFAULT NULL,
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信缓存表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` bigint(20) NOT NULL COMMENT '用户编号：雪花算法ID',
  `MOBILE` varchar(100) NOT NULL COMMENT '手机号码',
  `PASSWORD` varchar(500) NOT NULL COMMENT '登录密码',
  `CREATE_AT` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

