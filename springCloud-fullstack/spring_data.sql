/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : spring_data

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2019-07-12 08:48:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cra_person`
-- ----------------------------
DROP TABLE IF EXISTS `cra_person`;
CREATE TABLE `cra_person` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cra_person
-- ----------------------------
INSERT INTO `cra_person` VALUES ('15', '张三4');
INSERT INTO `cra_person` VALUES ('16', '张三5');
INSERT INTO `cra_person` VALUES ('17', '王五');
INSERT INTO `cra_person` VALUES ('21', '张三3');
