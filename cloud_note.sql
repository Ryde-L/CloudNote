/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : cloud_note

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 20/05/2020 22:26:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (4, 'Admin', '123');

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_book_id` int(11) NOT NULL COMMENT '笔记本id，BTREE索引',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '笔记名',
  `is_has_pwd` int(11) NOT NULL DEFAULT 0 COMMENT '是否加密',
  `pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '笔记查看密码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `note_book_id`(`note_book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '笔记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES (89, 59, '1', 0, NULL);

-- ----------------------------
-- Table structure for note_book
-- ----------------------------
DROP TABLE IF EXISTS `note_book`;
CREATE TABLE `note_book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id，BTREE索引',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '笔记本名',
  `deletable` int(11) NOT NULL COMMENT '用户是否可以删除 0否 1可',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '笔记本表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for note_content
-- ----------------------------
DROP TABLE IF EXISTS `note_content`;
CREATE TABLE `note_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NOT NULL COMMENT '笔记id，BTREE索引',
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '笔记内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `note_id`(`note_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '笔记内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for note_tag
-- ----------------------------
DROP TABLE IF EXISTS `note_tag`;
CREATE TABLE `note_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NOT NULL COMMENT '笔记id，BTREE索引',
  `tag` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '标签',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `note_id`(`note_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '笔记标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for recycle_bin
-- ----------------------------
DROP TABLE IF EXISTS `recycle_bin`;
CREATE TABLE `recycle_bin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id，BTREE索引',
  `note_id` int(11) NOT NULL COMMENT '笔记id，BTREE索引',
  `note_book_id` int(11) NOT NULL COMMENT '笔记本id，BTREE索引',
  `note_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '笔记名',
  `throw_away_time` datetime(0) NOT NULL COMMENT '放入废纸篓的时间',
  `clear_time` datetime(0) NOT NULL COMMENT '清除笔记的时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `note_id`(`note_id`) USING BTREE,
  INDEX `note_book_id`(`note_book_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '废纸篓表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recycle_bin
-- ----------------------------
INSERT INTO `recycle_bin` VALUES (43, 4, 41, 1, '笔记2', '2020-02-27 19:45:54', '2020-03-12 19:45:54');
INSERT INTO `recycle_bin` VALUES (44, 4, 42, 34, '新的笔记', '2020-02-27 22:52:36', '2020-03-12 22:52:36');
INSERT INTO `recycle_bin` VALUES (45, 4, 58, 37, '百度', '2020-04-24 11:38:55', '2020-05-08 11:38:55');

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id，BTREE索引',
  `note_id` int(11) NOT NULL COMMENT '笔记id，BTREE索引',
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '分享生成的链接',
  `is_has_pwd` int(11) NOT NULL DEFAULT 0 COMMENT '是否密码加密 0否 1是',
  `pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `limit_type` int(11) NOT NULL DEFAULT 0 COMMENT '分享的限制，0是无限制 1是天数',
  `limit_content` int(11) NULL DEFAULT NULL COMMENT '分享多少天',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(11) NOT NULL COMMENT '分享状态 0失效 1正常 3其它',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `link`(`link`) USING BTREE,
  INDEX `note_id`(`note_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '分享表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码,md5加密',
  `gender` int(11) NOT NULL DEFAULT 0 COMMENT '性别 0不详 1男 2女',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '用户的状态，0封禁 1正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
