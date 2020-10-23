/*
    数据库SQL操作
    数据库版本：
*/
drop database if exists ccms;
create database ccms;
use ccms;

drop table if exists `user`;
create table `user`(
    `id` int(8) unsigned not null auto_increment,
    `username` varchar(255) default null,
    `password` varchar(255) default null,
    primary key (`id`)
) engine = InnoDB auto_increment = 3 default charset = utf8;

/*
    默认用户角色为教师、学生
*/
insert into `user` values(1,'teacher','e10adc3949ba59abbe56e057f20f883e');
insert into `user` values(2,'student','96e79218965eb72c92a549dd5a330112');


-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tId` int NOT NULL,
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `job` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL CHECK(job IN('教授','副教授','讲师')),
  `gender` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `introduction` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`tId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `sId` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `contact` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`sId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `subId` int NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tId` int NOT NULL,
  `remain_group_num` int NOT NULL CHECK(remain_group_num >= 0),
  PRIMARY KEY (`subId`, `tId`) USING BTREE,
  INDEX `fk_tId`(`tId`) USING BTREE,
  INDEX `subId`(`subId`) USING BTREE,
  CONSTRAINT `fk_tId` FOREIGN KEY (`tId`) REFERENCES `teacher` (`tId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `gId` int NOT NULL AUTO_INCREMENT,
  `sId_1` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sId_2` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sId_3` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sId_4` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sId_5` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sId_6` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`gId`, `sId_1`, `sId_2`, `sId_3`) USING BTREE,
  INDEX `fk_sId1`(`sId_1`) USING BTREE,
  INDEX `fk_sId2`(`sId_2`) USING BTREE,
  INDEX `fk_sId3`(`sId_3`) USING BTREE,
  INDEX `gId`(`gId`) USING BTREE,
  CONSTRAINT `fk_sId1` FOREIGN KEY (`sId_1`) REFERENCES `student` (`sId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sId2` FOREIGN KEY (`sId_2`) REFERENCES `student` (`sId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sId3` FOREIGN KEY (`sId_3`) REFERENCES `student` (`sId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for topicselect
-- ----------------------------
DROP TABLE IF EXISTS `topicselect`;
CREATE TABLE `topicselect`  (
  `tsId` int NOT NULL AUTO_INCREMENT,
  `subId` int NOT NULL,
  `gId` int NOT NULL,
  `completed` int NOT NULL,
  PRIMARY KEY (`tsId`, `subId`, `gId`) USING BTREE,
  INDEX `fk_subId`(`subId`) USING BTREE,
  INDEX `fk_gId`(`gId`) USING BTREE,
  INDEX `tsId`(`tsId`) USING BTREE,
  CONSTRAINT `fk_subId` FOREIGN KEY (`subId`) REFERENCES `subject` (`subId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_gId` FOREIGN KEY (`gId`) REFERENCES `group` (`gId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `scoreId` int NOT NULL AUTO_INCREMENT,
  `tsId` int NOT NULL,
  `score` int NOT NULL,
  PRIMARY KEY (`scoreId`, `tsId`) USING BTREE,
  INDEX `fk_tsId`(`tsId`) USING BTREE,
  CONSTRAINT `fk_tsId` FOREIGN KEY (`tsId`) REFERENCES `topicselect` (`tsId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table topic_select
-- ----------------------------
DROP TRIGGER IF EXISTS `Tri_Insert_topic_select`;
CREATE TRIGGER `Tri_Insert_topic_select` BEFORE INSERT ON `topicselect` FOR EACH ROW BEGIN
   UPDATE `subject` SET remain_group_num = remain_group_num-1 WHERE subId=NEW.subId AND remain_group_num>=0;
END

-- ----------------------------
-- Triggers structure for table group
-- ----------------------------
DROP TRIGGER IF EXISTS `Tri_add_member`;
CREATE TRIGGER `Tri_add_member` BEFORE UPDATE ON `group` FOR EACH ROW BEGIN
   IF(OLD.sId_4 = null) THEN
	   UPDATE `group` SET OLD.sId_4=NEW.sId_4 WHERE OLD.sId_1=NEW.sId_1;
	 ELSEIF(OLD.sId_5 = null) THEN
	   UPDATE `group` SET OLD.sId_5=NEW.sId_5 WHERE OLD.sId_1=NEW.sId_1;
	 ELSEIF(OLD.sId_6 = null) THEN
	   UPDATE `group` SET OLD.sId_6=NEW.sId_6 WHERE OLD.sId_1=NEW.sId_1;
	 END IF;
END

-- ----------------------------
-- Data For Test
-- ----------------------------
insert into `student` values('2018091601004','d13c68fbd22a68bede58f109db3a8369','男','13067459646');
insert into `teacher` values('1','liuqiao','d13c68fbd22a68bede58f109db3a8369','13067459646','1009712456@qq.com','教授','男','青千');
