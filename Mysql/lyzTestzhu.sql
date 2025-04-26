/*
Navicat MySQL Data Transfer

Source Server         : hadoop102
Source Server Version : 50716
Source Host           : hadoop102:3306
Source Database       : lyzTestzhu

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2025-01-06 14:54:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '员工的唯一标识',
  `name` varchar(50) NOT NULL COMMENT '员工姓名',
  `gender` enum('MALE','FEMALE') DEFAULT NULL COMMENT '性别，MALE为男性，FEMALE为女性',
  `contact_info` varchar(100) DEFAULT NULL COMMENT '联系方式，例如手机号',
  `position` varchar(50) DEFAULT NULL COMMENT '职位，例如配送员、司机等',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='存储员工的基本信息';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '张三', 'MALE', '13800138000', '司机');
INSERT INTO `employee` VALUES ('3', '王五', 'MALE', '13800138002', '仓库管理员');
INSERT INTO `employee` VALUES ('4', '王伟', 'MALE', '13100084729', '配送员');
INSERT INTO `employee` VALUES ('6', '赵六', 'MALE', '13800000004', '配送员');
INSERT INTO `employee` VALUES ('7', '孙七', 'FEMALE', '13800000005', '配送员');
INSERT INTO `employee` VALUES ('8', '杨八', 'MALE', '13800000006', '司机');
INSERT INTO `employee` VALUES ('9', '刘九', 'MALE', '13800000007', '配送员');
INSERT INTO `employee` VALUES ('10', '陈十', 'FEMALE', '13800000008', '仓库管理员');
INSERT INTO `employee` VALUES ('11', '吴一', 'MALE', '13800000009', '配送员');
INSERT INTO `employee` VALUES ('12', '郑二', 'FEMALE', '13800000010', '配送员');
INSERT INTO `employee` VALUES ('13', '钱三', 'MALE', '13800000011', '司机');
INSERT INTO `employee` VALUES ('14', '孙四', 'FEMALE', '13800000012', '配送员');
INSERT INTO `employee` VALUES ('15', '李五', 'MALE', '13800000013', '仓库管理员');
INSERT INTO `employee` VALUES ('16', '王六', 'FEMALE', '13800000014', '配送员');
INSERT INTO `employee` VALUES ('17', '赵七', 'MALE', '13800000015', '配送员');
INSERT INTO `employee` VALUES ('18', '陈八', 'FEMALE', '13800000016', '司机');
INSERT INTO `employee` VALUES ('19', '林九', 'MALE', '13800000017', '仓库管理员');
INSERT INTO `employee` VALUES ('20', '郭一', 'MALE', '13800000019', '配送员');
INSERT INTO `employee` VALUES ('21', '黄二', 'FEMALE', '13800000020', '司机');
INSERT INTO `employee` VALUES ('22', '高三', 'MALE', '13800000021', '仓库管理员');
INSERT INTO `employee` VALUES ('23', '周五', 'MALE', '13800000023', '配送员');
INSERT INTO `employee` VALUES ('24', '郑六', 'FEMALE', '13800000024', '司机');
INSERT INTO `employee` VALUES ('25', '刘七', 'MALE', '13800000025', '仓库管理员');
INSERT INTO `employee` VALUES ('26', '吴九', 'MALE', '13800000027', '配送员');
INSERT INTO `employee` VALUES ('27', '科比', 'MALE', '13100064965', '配送员');

-- ----------------------------
-- Table structure for employee_order_count
-- ----------------------------
DROP TABLE IF EXISTS `employee_order_count`;
CREATE TABLE `employee_order_count` (
  `employee_name` varchar(255) NOT NULL,
  `order_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`employee_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of employee_order_count
-- ----------------------------
INSERT INTO `employee_order_count` VALUES ('刘九', '1');
INSERT INTO `employee_order_count` VALUES ('吴一', '2');
INSERT INTO `employee_order_count` VALUES ('吴九', '3');
INSERT INTO `employee_order_count` VALUES ('周五', '4');
INSERT INTO `employee_order_count` VALUES ('孙七', '4');
INSERT INTO `employee_order_count` VALUES ('孙四', '2');
INSERT INTO `employee_order_count` VALUES ('张三', '1');
INSERT INTO `employee_order_count` VALUES ('李五', '1');
INSERT INTO `employee_order_count` VALUES ('杨八', '2');
INSERT INTO `employee_order_count` VALUES ('林九', '4');
INSERT INTO `employee_order_count` VALUES ('王六', '2');
INSERT INTO `employee_order_count` VALUES ('赵七', '4');
INSERT INTO `employee_order_count` VALUES ('赵六', '2');
INSERT INTO `employee_order_count` VALUES ('郑二', '1');
INSERT INTO `employee_order_count` VALUES ('郑六', '1');
INSERT INTO `employee_order_count` VALUES ('郭一', '3');
INSERT INTO `employee_order_count` VALUES ('钱三', '4');
INSERT INTO `employee_order_count` VALUES ('陈八', '3');
INSERT INTO `employee_order_count` VALUES ('陈十', '1');
INSERT INTO `employee_order_count` VALUES ('黄二', '6');

-- ----------------------------
-- Table structure for logistics
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物流记录的唯一标识',
  `logistics_number` varchar(50) NOT NULL COMMENT '物流编号，唯一标识一条物流记录',
  `order_id` bigint(20) NOT NULL COMMENT '关联订单表的订单编号',
  `vehicle_id` bigint(20) DEFAULT NULL COMMENT '关联车辆表的车辆ID',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '关联员工表的配送员ID',
  `recipient` bigint(20) DEFAULT NULL COMMENT '关联用户表的收件人ID',
  `status` enum('待发货','运输中','已送达') DEFAULT '待发货' COMMENT '物流状态',
  `phone` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COMMENT='物流管理表，关联订单、车辆、员工和收件人';

-- ----------------------------
-- Records of logistics
-- ----------------------------
INSERT INTO `logistics` VALUES ('1', 'WLDD20240104001', '1', '2', '1', null, '待发货', null, '');
INSERT INTO `logistics` VALUES ('28', 'WLDD20240104051', '2', '8', '13', null, '运输中', null, '备注51');
INSERT INTO `logistics` VALUES ('29', 'WLDD20240104052', '3', '2', '20', null, '待发货', null, '备注52');
INSERT INTO `logistics` VALUES ('30', 'WLDD20240104053', '4', '14', '23', null, '运输中', null, '备注53');
INSERT INTO `logistics` VALUES ('31', 'WLDD20240104054', '5', '6', '12', null, '待发货', null, '备注54');
INSERT INTO `logistics` VALUES ('32', 'WLDD20240104055', '6', '15', '16', null, '运输中', null, '备注55');
INSERT INTO `logistics` VALUES ('33', 'WLDD20240104056', '7', '12', '6', null, '已送达', null, '备注56');
INSERT INTO `logistics` VALUES ('34', 'WLDD20240104057', '8', '1', '23', null, '运输中', null, '备注57');
INSERT INTO `logistics` VALUES ('35', 'WLDD20240104058', '9', '9', '18', null, '待发货', null, '备注58');
INSERT INTO `logistics` VALUES ('36', 'WLDD20240104059', '10', '8', '13', null, '已送达', null, '备注59');
INSERT INTO `logistics` VALUES ('37', 'WLDD20240104060', '11', '10', '7', null, '运输中', null, '备注60');
INSERT INTO `logistics` VALUES ('38', 'WLDD20240104061', '12', '10', '11', null, '待发货', null, '备注61');
INSERT INTO `logistics` VALUES ('39', 'WLDD20240104062', '13', '4', '6', null, '运输中', null, '备注62');
INSERT INTO `logistics` VALUES ('40', 'WLDD20240104063', '14', '10', '18', null, '已送达', null, '备注63');
INSERT INTO `logistics` VALUES ('41', 'WLDD20240104064', '15', '11', '20', null, '运输中', null, '备注64');
INSERT INTO `logistics` VALUES ('42', 'WLDD20240104065', '16', '13', '9', null, '待发货', null, '备注65');
INSERT INTO `logistics` VALUES ('43', 'WLDD20240104066', '17', '8', '19', null, '已送达', null, '备注66');
INSERT INTO `logistics` VALUES ('44', 'WLDD20240104067', '18', '11', '14', null, '运输中', null, '备注67');
INSERT INTO `logistics` VALUES ('45', 'WLDD20240104068', '19', '15', '7', null, '待发货', null, '备注68');
INSERT INTO `logistics` VALUES ('46', 'WLDD20240104069', '20', '9', '10', null, '运输中', null, '备注69');
INSERT INTO `logistics` VALUES ('47', 'WLDD20240104070', '21', '14', '21', null, '已送达', null, '备注70');
INSERT INTO `logistics` VALUES ('48', 'WLDD20240104071', '22', '4', '19', null, '待发货', null, '备注71');
INSERT INTO `logistics` VALUES ('49', 'WLDD20240104072', '23', '1', '23', null, '运输中', null, '备注72');
INSERT INTO `logistics` VALUES ('50', 'WLDD20240104073', '24', '4', '8', null, '已送达', null, '备注73');
INSERT INTO `logistics` VALUES ('51', 'WLDD20240104074', '25', '9', '24', null, '待发货', null, '备注74');
INSERT INTO `logistics` VALUES ('52', 'WLDD20240104075', '26', '15', '15', null, '运输中', null, '备注75');
INSERT INTO `logistics` VALUES ('53', 'WLDD20240104076', '27', '13', '7', null, '已送达', null, '备注76');
INSERT INTO `logistics` VALUES ('54', 'WLDD20240104077', '28', '14', '17', null, '运输中', null, '备注77');
INSERT INTO `logistics` VALUES ('55', 'WLDD20240104078', '29', '11', '21', null, '待发货', null, '备注78');
INSERT INTO `logistics` VALUES ('56', 'WLDD20240104079', '30', '10', '19', null, '运输中', null, '备注79');
INSERT INTO `logistics` VALUES ('57', 'WLDD20240104080', '31', '14', '17', null, '已送达', null, '备注80');
INSERT INTO `logistics` VALUES ('58', 'WLDD20240104081', '32', '15', '11', null, '运输中', null, '备注81');
INSERT INTO `logistics` VALUES ('59', 'WLDD20240104082', '33', '11', '13', null, '待发货', null, '备注82');
INSERT INTO `logistics` VALUES ('60', 'WLDD20240104083', '34', '15', '17', null, '已送达', null, '备注83');
INSERT INTO `logistics` VALUES ('61', 'WLDD20240104084', '35', '2', '13', null, '运输中', null, '备注84');
INSERT INTO `logistics` VALUES ('62', 'WLDD20240104085', '36', '1', '23', null, '待发货', null, '备注85');
INSERT INTO `logistics` VALUES ('63', 'WLDD20240104086', '37', '13', '18', null, '运输中', null, '备注86');
INSERT INTO `logistics` VALUES ('64', 'WLDD20240104087', '38', '7', '26', null, '运输中', null, '备注87');
INSERT INTO `logistics` VALUES ('65', 'WLDD20240104088', '39', '2', '26', null, '待发货', null, '备注88');
INSERT INTO `logistics` VALUES ('66', 'WLDD20240104089', '40', '11', '20', null, '运输中', null, '备注89');
INSERT INTO `logistics` VALUES ('67', 'WLDD20240104090', '41', '13', '7', null, '已送达', null, '备注90');
INSERT INTO `logistics` VALUES ('68', 'WLDD20240104091', '42', '11', '8', null, '运输中', null, '备注91');
INSERT INTO `logistics` VALUES ('69', 'WLDD20240104092', '43', '4', '21', null, '待发货', null, '备注92');
INSERT INTO `logistics` VALUES ('70', 'WLDD20240104093', '44', '9', '16', null, '运输中', null, '备注93');
INSERT INTO `logistics` VALUES ('71', 'WLDD20240104094', '45', '12', '17', null, '运输中', null, '备注94');
INSERT INTO `logistics` VALUES ('72', 'WLDD20240104095', '46', '9', '14', null, '运输中', null, '备注95');
INSERT INTO `logistics` VALUES ('73', 'WLDD20240104096', '47', '2', '21', null, '运输中', null, '备注96');
INSERT INTO `logistics` VALUES ('74', 'WLDD20240104097', '48', '1', '26', null, '运输中', null, '备注97');
INSERT INTO `logistics` VALUES ('75', 'WLDD20240104098', '49', '3', '21', null, '运输中', null, '备注98');
INSERT INTO `logistics` VALUES ('76', 'WLDD20240104099', '50', '6', '19', null, '运输中', null, '备注99');
INSERT INTO `logistics` VALUES ('77', 'WLDD20240104100', '51', '6', '21', null, '运输中', null, '备注100');
INSERT INTO `logistics` VALUES ('78', 'WLDD20240104001', '1', '7', '4', null, '待发货', null, '注意时间');

-- ----------------------------
-- Table structure for logistics_employee_summary
-- ----------------------------
DROP TABLE IF EXISTS `logistics_employee_summary`;
CREATE TABLE `logistics_employee_summary` (
  `logistics_id` int(11) NOT NULL,
  `logistics_number` varchar(255) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `employee_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logistics_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of logistics_employee_summary
-- ----------------------------
INSERT INTO `logistics_employee_summary` VALUES ('1', 'WLDD20240104001', '1', '待发货', '1', '张三', 'MALE', '13800138000', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('28', 'WLDD20240104051', '2', '运输中', '13', '钱三', 'MALE', '13800000011', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('29', 'WLDD20240104052', '3', '待发货', '20', '郭一', 'MALE', '13800000019', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('30', 'WLDD20240104053', '4', '运输中', '23', '周五', 'MALE', '13800000023', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('31', 'WLDD20240104054', '5', '待发货', '12', '郑二', 'FEMALE', '13800000010', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('32', 'WLDD20240104055', '6', '运输中', '16', '王六', 'FEMALE', '13800000014', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('33', 'WLDD20240104056', '7', '已送达', '6', '赵六', 'MALE', '13800000004', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('34', 'WLDD20240104057', '8', '运输中', '23', '周五', 'MALE', '13800000023', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('35', 'WLDD20240104058', '9', '待发货', '18', '陈八', 'FEMALE', '13800000016', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('36', 'WLDD20240104059', '10', '已送达', '13', '钱三', 'MALE', '13800000011', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('37', 'WLDD20240104060', '11', '运输中', '7', '孙七', 'FEMALE', '13800000005', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('38', 'WLDD20240104061', '12', '待发货', '11', '吴一', 'MALE', '13800000009', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('39', 'WLDD20240104062', '13', '运输中', '6', '赵六', 'MALE', '13800000004', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('40', 'WLDD20240104063', '14', '已送达', '18', '陈八', 'FEMALE', '13800000016', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('41', 'WLDD20240104064', '15', '运输中', '20', '郭一', 'MALE', '13800000019', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('42', 'WLDD20240104065', '16', '待发货', '9', '刘九', 'MALE', '13800000007', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('43', 'WLDD20240104066', '17', '已送达', '19', '林九', 'MALE', '13800000017', '仓库管理员');
INSERT INTO `logistics_employee_summary` VALUES ('44', 'WLDD20240104067', '18', '运输中', '14', '孙四', 'FEMALE', '13800000012', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('45', 'WLDD20240104068', '19', '待发货', '7', '孙七', 'FEMALE', '13800000005', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('46', 'WLDD20240104069', '20', '运输中', '10', '陈十', 'FEMALE', '13800000008', '仓库管理员');
INSERT INTO `logistics_employee_summary` VALUES ('47', 'WLDD20240104070', '21', '已送达', '21', '黄二', 'FEMALE', '13800000020', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('48', 'WLDD20240104071', '22', '待发货', '19', '林九', 'MALE', '13800000017', '仓库管理员');
INSERT INTO `logistics_employee_summary` VALUES ('49', 'WLDD20240104072', '23', '运输中', '23', '周五', 'MALE', '13800000023', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('50', 'WLDD20240104073', '24', '已送达', '8', '杨八', 'MALE', '13800000006', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('51', 'WLDD20240104074', '25', '待发货', '24', '郑六', 'FEMALE', '13800000024', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('52', 'WLDD20240104075', '26', '运输中', '15', '李五', 'MALE', '13800000013', '仓库管理员');
INSERT INTO `logistics_employee_summary` VALUES ('53', 'WLDD20240104076', '27', '已送达', '7', '孙七', 'FEMALE', '13800000005', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('54', 'WLDD20240104077', '28', '运输中', '17', '赵七', 'MALE', '13800000015', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('55', 'WLDD20240104078', '29', '待发货', '21', '黄二', 'FEMALE', '13800000020', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('56', 'WLDD20240104079', '30', '运输中', '19', '林九', 'MALE', '13800000017', '仓库管理员');
INSERT INTO `logistics_employee_summary` VALUES ('57', 'WLDD20240104080', '31', '已送达', '17', '赵七', 'MALE', '13800000015', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('58', 'WLDD20240104081', '32', '运输中', '11', '吴一', 'MALE', '13800000009', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('59', 'WLDD20240104082', '33', '待发货', '13', '钱三', 'MALE', '13800000011', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('60', 'WLDD20240104083', '34', '已送达', '17', '赵七', 'MALE', '13800000015', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('61', 'WLDD20240104084', '35', '运输中', '13', '钱三', 'MALE', '13800000011', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('62', 'WLDD20240104085', '36', '待发货', '23', '周五', 'MALE', '13800000023', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('63', 'WLDD20240104086', '37', '运输中', '18', '陈八', 'FEMALE', '13800000016', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('64', 'WLDD20240104087', '38', '运输中', '26', '吴九', 'MALE', '13800000027', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('65', 'WLDD20240104088', '39', '待发货', '26', '吴九', 'MALE', '13800000027', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('66', 'WLDD20240104089', '40', '运输中', '20', '郭一', 'MALE', '13800000019', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('67', 'WLDD20240104090', '41', '已送达', '7', '孙七', 'FEMALE', '13800000005', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('68', 'WLDD20240104091', '42', '运输中', '8', '杨八', 'MALE', '13800000006', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('69', 'WLDD20240104092', '43', '待发货', '21', '黄二', 'FEMALE', '13800000020', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('70', 'WLDD20240104093', '44', '运输中', '16', '王六', 'FEMALE', '13800000014', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('71', 'WLDD20240104094', '45', '运输中', '17', '赵七', 'MALE', '13800000015', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('72', 'WLDD20240104095', '46', '运输中', '14', '孙四', 'FEMALE', '13800000012', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('73', 'WLDD20240104096', '47', '运输中', '21', '黄二', 'FEMALE', '13800000020', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('74', 'WLDD20240104097', '48', '运输中', '26', '吴九', 'MALE', '13800000027', '配送员');
INSERT INTO `logistics_employee_summary` VALUES ('75', 'WLDD20240104098', '49', '运输中', '21', '黄二', 'FEMALE', '13800000020', '司机');
INSERT INTO `logistics_employee_summary` VALUES ('76', 'WLDD20240104099', '50', '运输中', '19', '林九', 'MALE', '13800000017', '仓库管理员');
INSERT INTO `logistics_employee_summary` VALUES ('77', 'WLDD20240104100', '51', '运输中', '21', '黄二', 'FEMALE', '13800000020', '司机');

-- ----------------------------
-- Table structure for logistics_status_summary
-- ----------------------------
DROP TABLE IF EXISTS `logistics_status_summary`;
CREATE TABLE `logistics_status_summary` (
  `status` varchar(255) NOT NULL,
  `status_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of logistics_status_summary
-- ----------------------------
INSERT INTO `logistics_status_summary` VALUES ('已送达', '10');
INSERT INTO `logistics_status_summary` VALUES ('待发货', '14');
INSERT INTO `logistics_status_summary` VALUES ('运输中', '27');

-- ----------------------------
-- Table structure for logistics_vehicle_summary
-- ----------------------------
DROP TABLE IF EXISTS `logistics_vehicle_summary`;
CREATE TABLE `logistics_vehicle_summary` (
  `logistics_id` bigint(20) NOT NULL,
  `logistics_number` varchar(255) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `vehicle_id` bigint(20) DEFAULT NULL,
  `license_plate` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `vehicle_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logistics_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of logistics_vehicle_summary
-- ----------------------------
INSERT INTO `logistics_vehicle_summary` VALUES ('1', 'WLDD20240104001', '1', null, '2', '沪B67890', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('28', 'WLDD20240104051', '2', null, '8', '晋C12345', '面包车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('29', 'WLDD20240104052', '3', null, '2', '沪B67890', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('30', 'WLDD20240104053', '4', null, '14', '晋I12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('31', 'WLDD20240104054', '5', null, '6', '晋A12345', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('32', 'WLDD20240104055', '6', null, '15', '晋J12345', '小轿车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('33', 'WLDD20240104056', '7', null, '12', '晋G12345', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('34', 'WLDD20240104057', '8', null, '1', '京A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('35', 'WLDD20240104058', '9', null, '9', '晋D12345', '卡车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('36', 'WLDD20240104059', '10', null, '8', '晋C12345', '面包车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('37', 'WLDD20240104060', '11', null, '10', '晋E12345', '小轿车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('38', 'WLDD20240104061', '12', null, '10', '晋E12345', '小轿车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('39', 'WLDD20240104062', '13', null, '4', '晋A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('40', 'WLDD20240104063', '14', null, '10', '晋E12345', '小轿车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('41', 'WLDD20240104064', '15', null, '11', '晋F12345', '货车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('42', 'WLDD20240104065', '16', null, '13', '晋H12345', '面包车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('43', 'WLDD20240104066', '17', null, '8', '晋C12345', '面包车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('44', 'WLDD20240104067', '18', null, '11', '晋F12345', '货车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('45', 'WLDD20240104068', '19', null, '15', '晋J12345', '小轿车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('46', 'WLDD20240104069', '20', null, '9', '晋D12345', '卡车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('47', 'WLDD20240104070', '21', null, '14', '晋I12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('48', 'WLDD20240104071', '22', null, '4', '晋A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('49', 'WLDD20240104072', '23', null, '1', '京A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('50', 'WLDD20240104073', '24', null, '4', '晋A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('51', 'WLDD20240104074', '25', null, '9', '晋D12345', '卡车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('52', 'WLDD20240104075', '26', null, '15', '晋J12345', '小轿车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('53', 'WLDD20240104076', '27', null, '13', '晋H12345', '面包车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('54', 'WLDD20240104077', '28', null, '14', '晋I12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('55', 'WLDD20240104078', '29', null, '11', '晋F12345', '货车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('56', 'WLDD20240104079', '30', null, '10', '晋E12345', '小轿车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('57', 'WLDD20240104080', '31', null, '14', '晋I12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('58', 'WLDD20240104081', '32', null, '15', '晋J12345', '小轿车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('59', 'WLDD20240104082', '33', null, '11', '晋F12345', '货车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('60', 'WLDD20240104083', '34', null, '15', '晋J12345', '小轿车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('61', 'WLDD20240104084', '35', null, '2', '沪B67890', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('62', 'WLDD20240104085', '36', null, '1', '京A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('63', 'WLDD20240104086', '37', null, '13', '晋H12345', '面包车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('64', 'WLDD20240104087', '38', null, '7', '晋B12345', '货车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('65', 'WLDD20240104088', '39', null, '2', '沪B67890', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('66', 'WLDD20240104089', '40', null, '11', '晋F12345', '货车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('67', 'WLDD20240104090', '41', null, '13', '晋H12345', '面包车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('68', 'WLDD20240104091', '42', null, '11', '晋F12345', '货车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('69', 'WLDD20240104092', '43', null, '4', '晋A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('70', 'WLDD20240104093', '44', null, '9', '晋D12345', '卡车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('71', 'WLDD20240104094', '45', null, '12', '晋G12345', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('72', 'WLDD20240104095', '46', null, '9', '晋D12345', '卡车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('73', 'WLDD20240104096', '47', null, '2', '沪B67890', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('74', 'WLDD20240104097', '48', null, '1', '京A12345', '货车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('75', 'WLDD20240104098', '49', null, '3', '粤C54321', '面包车', 'AVAILABLE');
INSERT INTO `logistics_vehicle_summary` VALUES ('76', 'WLDD20240104099', '50', null, '6', '晋A12345', '卡车', 'IN_USE');
INSERT INTO `logistics_vehicle_summary` VALUES ('77', 'WLDD20240104100', '51', null, '6', '晋A12345', '卡车', 'IN_USE');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单的唯一标识',
  `order_number` varchar(50) NOT NULL COMMENT '订单编号，唯一标识一个订单',
  `recipient_name` varchar(50) NOT NULL COMMENT '收货人姓名',
  `recipient_phone` varchar(15) NOT NULL COMMENT '收货人联系电话',
  `address` varchar(255) NOT NULL COMMENT '收货地址',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `status` enum('待发货','已发货','已完成','已取消') DEFAULT '待发货' COMMENT '订单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COMMENT='订单管理表';

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', 'DD20240104001', '李四', '13900139000', '北京市', '2025-01-05 00:07:38', '待发货');
INSERT INTO `orders` VALUES ('7', 'ON000006', '张伟', '13500000006', '河北省', '2025-01-06 15:00:00', '待发货');
INSERT INTO `orders` VALUES ('8', 'ON000007', '李静', '13500000007', '山东省', '2025-01-07 16:00:00', '已发货');
INSERT INTO `orders` VALUES ('9', 'ON000008', '王杰', '13500000008', '陕西省', '2025-01-08 17:00:00', '已完成');
INSERT INTO `orders` VALUES ('10', 'ON000009', '陈丽', '13500000009', '广西自治区', '2025-01-09 18:00:00', '已取消');
INSERT INTO `orders` VALUES ('11', 'ON000010', '赵伟', '13500000010', '内蒙古自治区', '2025-01-10 19:00:00', '待发货');
INSERT INTO `orders` VALUES ('12', 'ON000011', '杨磊', '13500000011', '河南省', '2025-01-11 20:00:00', '已发货');
INSERT INTO `orders` VALUES ('13', 'ON000012', '吴霞', '13500000012', '辽宁省', '2025-01-12 21:00:00', '已完成');
INSERT INTO `orders` VALUES ('14', 'ON000013', '刘艳', '13500000013', '四川省', '2025-01-13 22:00:00', '已取消');
INSERT INTO `orders` VALUES ('15', 'ON000014', '王磊', '13500000014', '吉林省', '2025-01-14 23:00:00', '待发货');
INSERT INTO `orders` VALUES ('16', 'ON000015', '张欣', '13500000015', '青海省', '2025-01-15 10:00:00', '已发货');
INSERT INTO `orders` VALUES ('17', 'ON000016', '赵晨', '13500000016', '贵州省', '2025-01-16 11:00:00', '已完成');
INSERT INTO `orders` VALUES ('18', 'ON000017', '李娜', '13500000017', '甘肃省', '2025-01-17 12:00:00', '已取消');
INSERT INTO `orders` VALUES ('19', 'ON000018', '吴宇', '13500000018', '福建省', '2025-01-18 13:00:00', '待发货');
INSERT INTO `orders` VALUES ('20', 'ON000019', '孙斌', '13500000019', '江西省', '2025-01-19 14:00:00', '已发货');
INSERT INTO `orders` VALUES ('21', 'ON000020', '刘波', '13500000020', '安徽省', '2025-01-20 15:00:00', '已完成');
INSERT INTO `orders` VALUES ('22', 'ON000021', '张怡', '13500000021', '湖北省', '2025-01-21 16:00:00', '已取消');
INSERT INTO `orders` VALUES ('23', 'ON000022', '李丹', '13500000022', '湖南省', '2025-01-22 17:00:00', '待发货');
INSERT INTO `orders` VALUES ('24', 'ON000023', '王鑫', '13500000023', '云南省', '2025-01-23 18:00:00', '已发货');
INSERT INTO `orders` VALUES ('25', 'ON000024', '郑洁', '13500000024', '重庆市', '2025-01-24 19:00:00', '已完成');
INSERT INTO `orders` VALUES ('26', 'ON000025', '周俊', '13500000025', '北京市', '2025-01-25 20:00:00', '已取消');
INSERT INTO `orders` VALUES ('27', 'ON000026', '郑思', '13500000026', '天津市', '2025-01-26 21:00:00', '待发货');
INSERT INTO `orders` VALUES ('28', 'ON000027', '唐娜', '13500000027', '上海市', '2025-01-27 22:00:00', '已发货');
INSERT INTO `orders` VALUES ('29', 'ON000028', '孙明', '13500000028', '浙江省', '2025-01-28 23:00:00', '已完成');
INSERT INTO `orders` VALUES ('30', 'ON000029', '陈涛', '13500000029', '广东省', '2025-01-29 10:00:00', '已取消');
INSERT INTO `orders` VALUES ('31', 'ON000030', '赵玲', '13500000030', '江苏省', '2025-01-30 11:00:00', '待发货');
INSERT INTO `orders` VALUES ('32', 'ON000031', '李翠', '13500000031', '四川省', '2025-01-31 12:00:00', '已发货');
INSERT INTO `orders` VALUES ('33', 'ON000032', '吴琳', '13500000032', '山东省', '2025-02-01 13:00:00', '已完成');
INSERT INTO `orders` VALUES ('34', 'ON000033', '黄彬', '13500000033', '吉林省', '2025-02-02 14:00:00', '已取消');
INSERT INTO `orders` VALUES ('35', 'ON000034', '李华', '13500000034', '河南省', '2025-02-03 15:00:00', '待发货');
INSERT INTO `orders` VALUES ('36', 'ON000035', '王晨', '13500000035', '江西省', '2025-02-04 16:00:00', '已发货');
INSERT INTO `orders` VALUES ('37', 'ON000036', '周超', '13500000036', '湖北省', '2025-02-05 17:00:00', '已完成');
INSERT INTO `orders` VALUES ('38', 'ON000037', '杨玥', '13500000037', '安徽省', '2025-02-06 18:00:00', '已取消');
INSERT INTO `orders` VALUES ('39', 'ON000038', '陈达', '13500000038', '重庆市', '2025-02-07 19:00:00', '待发货');
INSERT INTO `orders` VALUES ('40', 'ON000039', '高媛', '13500000039', '青海省', '2025-02-08 20:00:00', '已发货');
INSERT INTO `orders` VALUES ('41', 'ON000040', '郑洁', '13500000040', '内蒙古自治区', '2025-02-09 21:00:00', '已完成');
INSERT INTO `orders` VALUES ('42', 'ON000041', '王军', '13500000041', '云南省', '2025-02-10 22:00:00', '已取消');
INSERT INTO `orders` VALUES ('43', 'ON000042', '李杰', '13500000042', '新疆自治区', '2025-02-11 10:00:00', '待发货');
INSERT INTO `orders` VALUES ('44', 'ON000043', '黄玲', '13500000043', '广东省', '2025-02-12 11:00:00', '已发货');
INSERT INTO `orders` VALUES ('45', 'ON000044', '赵川', '13500000044', '陕西省', '2025-02-13 12:00:00', '已完成');
INSERT INTO `orders` VALUES ('46', 'ON000045', '刘浩', '13500000045', '浙江省', '2025-02-14 13:00:00', '已取消');
INSERT INTO `orders` VALUES ('47', 'ON000046', '张敏', '13500000046', '四川省', '2025-02-15 14:00:00', '待发货');
INSERT INTO `orders` VALUES ('48', 'ON000047', '李波', '13500000047', '山东省', '2025-02-16 15:00:00', '已发货');
INSERT INTO `orders` VALUES ('49', 'ON000048', '王峰', '13500000048', '河南省', '2025-02-17 16:00:00', '已完成');
INSERT INTO `orders` VALUES ('50', 'ON000049', '黄峰', '13500000049', '陕西省', '2025-02-18 17:00:00', '已取消');
INSERT INTO `orders` VALUES ('51', 'ON000050', '李涛', '13500000050', '福建省', '2025-02-19 18:00:00', '待发货');
INSERT INTO `orders` VALUES ('52', 'ON00100', '刘翔', '13500000100', '广西自治区', '2025-02-28 23:00:00', '已发货');
INSERT INTO `orders` VALUES ('53', 'ON00101', '王波', '13500000101', '湖南省', '2025-03-01 10:00:00', '已完成');
INSERT INTO `orders` VALUES ('54', 'ON00102', '李莉', '13500000102', '江西省', '2025-03-02 11:00:00', '待发货');
INSERT INTO `orders` VALUES ('55', 'ON00103', '张涛', '13500000103', '河北省', '2025-03-03 12:00:00', '已发货');
INSERT INTO `orders` VALUES ('56', 'ON00104', '吴丽', '13500000104', '辽宁省', '2025-03-04 13:00:00', '已完成');
INSERT INTO `orders` VALUES ('57', 'ON00105', '陈龙', '13500000105', '重庆市', '2025-03-05 14:00:00', '已取消');
INSERT INTO `orders` VALUES ('58', 'ON00106', '赵辉', '13500000106', '陕西省', '2025-03-06 15:00:00', '待发货');
INSERT INTO `orders` VALUES ('59', 'ON00107', '刘文', '13500000107', '安徽省', '2025-03-07 16:00:00', '已发货');
INSERT INTO `orders` VALUES ('60', 'ON00108', '周洁', '13500000108', '天津市', '2025-03-08 17:00:00', '已完成');
INSERT INTO `orders` VALUES ('61', 'ON00109', '郑国', '13500000109', '北京市', '2025-03-09 18:00:00', '已取消');
INSERT INTO `orders` VALUES ('62', 'ON00110', '王杰', '13500000110', '上海市', '2025-03-10 19:00:00', '待发货');
INSERT INTO `orders` VALUES ('63', 'ON00111', '李斌', '13500000111', '广东省', '2025-03-11 20:00:00', '已发货');
INSERT INTO `orders` VALUES ('64', 'ON00112', '孙亮', '13500000112', '河南省', '2025-03-12 21:00:00', '已完成');
INSERT INTO `orders` VALUES ('65', 'ON00113', '张静', '13500000113', '湖北省', '2025-03-13 22:00:00', '已取消');
INSERT INTO `orders` VALUES ('66', 'ON00114', '刘斌', '13500000114', '山西省', '2025-03-14 23:00:00', '待发货');
INSERT INTO `orders` VALUES ('67', 'ON00115', '王荣', '13500000115', '广西自治区', '2025-03-15 10:00:00', '已发货');
INSERT INTO `orders` VALUES ('68', 'ON00116', '赵丽', '13500000116', '浙江省', '2025-03-16 11:00:00', '已完成');
INSERT INTO `orders` VALUES ('69', 'ON00117', '李俊', '13500000117', '天津市', '2025-03-17 12:00:00', '已取消');
INSERT INTO `orders` VALUES ('70', 'ON00118', '陈颖', '13500000118', '四川省', '2025-03-18 13:00:00', '待发货');
INSERT INTO `orders` VALUES ('71', 'ON00119', '黄鹏', '13500000119', '江苏省', '2025-03-19 14:00:00', '已发货');
INSERT INTO `orders` VALUES ('72', 'ON00120', '周凯', '13500000120', '吉林省', '2025-03-20 15:00:00', '已完成');
INSERT INTO `orders` VALUES ('73', 'ON00121', '孙佳', '13500000121', '重庆市', '2025-03-21 16:00:00', '已取消');
INSERT INTO `orders` VALUES ('74', 'ON00122', '王伟', '13500000122', '河南省', '2025-03-22 17:00:00', '待发货');
INSERT INTO `orders` VALUES ('75', 'ON00123', '张琳', '13500000123', '北京市', '2025-03-23 18:00:00', '已发货');
INSERT INTO `orders` VALUES ('76', 'ON00124', '李淑', '13500000124', '山东省', '2025-03-24 19:00:00', '已完成');
INSERT INTO `orders` VALUES ('77', 'ON00125', '赵军', '13500000125', '福建省', '2025-03-25 20:00:00', '已取消');
INSERT INTO `orders` VALUES ('78', 'ON00126', '吴浩', '13500000126', '河北省', '2025-03-26 21:00:00', '待发货');
INSERT INTO `orders` VALUES ('79', 'ON00127', '周芳', '13500000127', '吉林省', '2025-03-27 22:00:00', '已发货');
INSERT INTO `orders` VALUES ('80', 'ON00128', '郑波', '13500000128', '广东省', '2025-03-28 23:00:00', '已完成');
INSERT INTO `orders` VALUES ('81', 'ON00129', '李俊', '13500000129', '四川省', '2025-03-29 10:00:00', '已取消');
INSERT INTO `orders` VALUES ('82', 'ON00130', '周敏', '13500000130', '安徽省', '2025-03-30 11:00:00', '待发货');
INSERT INTO `orders` VALUES ('83', 'ON00131', '王杰', '13500000131', '湖南省', '2025-03-31 12:00:00', '已发货');
INSERT INTO `orders` VALUES ('84', 'ON00132', '郑磊', '13500000132', '江西省', '2025-04-01 13:00:00', '已完成');
INSERT INTO `orders` VALUES ('85', 'ON00133', '张明', '13500000133', '青海省', '2025-04-02 14:00:00', '已取消');
INSERT INTO `orders` VALUES ('86', 'ON00134', '李琪', '13500000134', '广东省', '2025-04-03 15:00:00', '待发货');
INSERT INTO `orders` VALUES ('87', 'ON00135', '王浩', '13500000135', '甘肃省', '2025-04-04 16:00:00', '已发货');
INSERT INTO `orders` VALUES ('88', 'ON00136', '孙锋', '13500000136', '江苏省', '2025-04-05 17:00:00', '已完成');
INSERT INTO `orders` VALUES ('89', 'ON00137', '李光', '13500000137', '山西省', '2025-04-06 18:00:00', '已取消');
INSERT INTO `orders` VALUES ('90', 'ON00138', '赵光', '13500000138', '浙江省', '2025-04-07 19:00:00', '待发货');
INSERT INTO `orders` VALUES ('91', 'ON00139', '刘波', '13500000139', '福建省', '2025-04-08 20:00:00', '已发货');
INSERT INTO `orders` VALUES ('92', 'ON00140', '王鑫', '13500000140', '重庆市', '2025-04-09 21:00:00', '已完成');
INSERT INTO `orders` VALUES ('93', 'ON00141', '陈娟', '13500000141', '四川省', '2025-04-10 22:00:00', '已取消');
INSERT INTO `orders` VALUES ('94', 'ON00142', '李娜', '13500000142', '北京市', '2025-04-11 23:00:00', '待发货');
INSERT INTO `orders` VALUES ('95', 'ON00143', '周丹', '13500000143', '江西省', '2025-04-12 10:00:00', '已发货');
INSERT INTO `orders` VALUES ('96', 'ON00144', '张腾', '13500000144', '河南省', '2025-04-13 11:00:00', '已完成');
INSERT INTO `orders` VALUES ('97', 'ON00145', '赵雷', '13500000145', '广东省', '2025-04-14 12:00:00', '已取消');
INSERT INTO `orders` VALUES ('98', 'ON00146', '王刚', '13500000146', '陕西省', '2025-04-15 13:00:00', '待发货');
INSERT INTO `orders` VALUES ('99', 'ON00147', '李康', '13500000147', '江苏省', '2025-04-16 14:00:00', '已发货');
INSERT INTO `orders` VALUES ('100', 'ON00148', '郑畅', '13500000148', '安徽省', '2025-04-17 15:00:00', '已完成');
INSERT INTO `orders` VALUES ('101', 'ON00149', '张迪', '13500000149', '江西省', '2025-04-18 16:00:00', '已取消');
INSERT INTO `orders` VALUES ('102', 'ON00150', '赵勇', '13500000150', '山东省', '2025-04-19 17:00:00', '待发货');
INSERT INTO `orders` VALUES ('103', 'DD202401060134', '李四', '13100083498', '北京市', '2025-01-06 01:35:03', '待发货');

-- ----------------------------
-- Table structure for province_order_summary
-- ----------------------------
DROP TABLE IF EXISTS `province_order_summary`;
CREATE TABLE `province_order_summary` (
  `province` varchar(255) NOT NULL,
  `order_count` int(255) DEFAULT NULL,
  PRIMARY KEY (`province`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of province_order_summary
-- ----------------------------
INSERT INTO `province_order_summary` VALUES ('上海市', '3');
INSERT INTO `province_order_summary` VALUES ('云南省', '2');
INSERT INTO `province_order_summary` VALUES ('内蒙古自治区', '2');
INSERT INTO `province_order_summary` VALUES ('北京市', '5');
INSERT INTO `province_order_summary` VALUES ('吉林省', '4');
INSERT INTO `province_order_summary` VALUES ('四川省', '6');
INSERT INTO `province_order_summary` VALUES ('天津市', '3');
INSERT INTO `province_order_summary` VALUES ('安徽省', '5');
INSERT INTO `province_order_summary` VALUES ('山东省', '5');
INSERT INTO `province_order_summary` VALUES ('山西省', '2');
INSERT INTO `province_order_summary` VALUES ('广东省', '6');
INSERT INTO `province_order_summary` VALUES ('广州市', '1');
INSERT INTO `province_order_summary` VALUES ('广西自治区', '3');
INSERT INTO `province_order_summary` VALUES ('新疆自治区', '1');
INSERT INTO `province_order_summary` VALUES ('江苏省', '4');
INSERT INTO `province_order_summary` VALUES ('江西省', '6');
INSERT INTO `province_order_summary` VALUES ('河北省', '3');
INSERT INTO `province_order_summary` VALUES ('河南省', '6');
INSERT INTO `province_order_summary` VALUES ('浙江省', '4');
INSERT INTO `province_order_summary` VALUES ('湖北省', '3');
INSERT INTO `province_order_summary` VALUES ('湖南省', '3');
INSERT INTO `province_order_summary` VALUES ('甘肃省', '2');
INSERT INTO `province_order_summary` VALUES ('福建省', '4');
INSERT INTO `province_order_summary` VALUES ('贵州省', '1');
INSERT INTO `province_order_summary` VALUES ('辽宁省', '2');
INSERT INTO `province_order_summary` VALUES ('重庆市', '5');
INSERT INTO `province_order_summary` VALUES ('陕西省', '5');
INSERT INTO `province_order_summary` VALUES ('青海省', '3');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名，用于登录或显示',
  `password` varchar(100) NOT NULL COMMENT '加密后的用户密码',
  `role` enum('ADMIN','USER') NOT NULL COMMENT '用户角色，ADMIN表示管理员，USER表示普通用户',
  `phone` varchar(15) DEFAULT NULL COMMENT '用户的手机号码',
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户的创建时间，自动生成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='存储用户的基本信息和角色信息';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin123', 'ADMIN', '13800138000', '2025-01-04 20:30:53');
INSERT INTO `user` VALUES ('4', 'lyz', '123456', 'USER', '13100064858', '2025-01-04 20:31:14');

-- ----------------------------
-- Table structure for vehicle
-- ----------------------------
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE `vehicle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车辆的唯一标识',
  `license_plate` varchar(50) NOT NULL COMMENT '车辆的车牌号，唯一标识一辆车',
  `model` varchar(50) DEFAULT NULL COMMENT '车辆型号，例如“卡车”、“货车”等',
  `status` enum('IN_USE','AVAILABLE') NOT NULL COMMENT '车辆状态：IN_USE（使用中）、AVAILABLE（可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='存储用于物流的车辆信息';

-- ----------------------------
-- Records of vehicle
-- ----------------------------
INSERT INTO `vehicle` VALUES ('1', '京A12345', '货车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('2', '沪B67890', '卡车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('4', '晋A12345', '货车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('6', '晋A12345', '卡车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('7', '晋B12345', '货车', 'AVAILABLE');
INSERT INTO `vehicle` VALUES ('8', '晋C12345', '面包车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('9', '晋D12345', '卡车', 'AVAILABLE');
INSERT INTO `vehicle` VALUES ('10', '晋E12345', '小轿车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('11', '晋F12345', '货车', 'AVAILABLE');
INSERT INTO `vehicle` VALUES ('12', '晋G12345', '卡车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('13', '晋H12345', '面包车', 'AVAILABLE');
INSERT INTO `vehicle` VALUES ('14', '晋I12345', '货车', 'IN_USE');
INSERT INTO `vehicle` VALUES ('15', '晋J12345', '小轿车', 'AVAILABLE');
INSERT INTO `vehicle` VALUES ('16', '京A88888', '货车', 'AVAILABLE');

-- ----------------------------
-- Table structure for vehicle_usage_count
-- ----------------------------
DROP TABLE IF EXISTS `vehicle_usage_count`;
CREATE TABLE `vehicle_usage_count` (
  `license_plate` varchar(255) NOT NULL,
  `usage_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`license_plate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of vehicle_usage_count
-- ----------------------------
INSERT INTO `vehicle_usage_count` VALUES ('京A12345', '4');
INSERT INTO `vehicle_usage_count` VALUES ('晋A12345', '7');
INSERT INTO `vehicle_usage_count` VALUES ('晋B12345', '1');
INSERT INTO `vehicle_usage_count` VALUES ('晋C12345', '3');
INSERT INTO `vehicle_usage_count` VALUES ('晋D12345', '5');
INSERT INTO `vehicle_usage_count` VALUES ('晋E12345', '4');
INSERT INTO `vehicle_usage_count` VALUES ('晋F12345', '6');
INSERT INTO `vehicle_usage_count` VALUES ('晋G12345', '2');
INSERT INTO `vehicle_usage_count` VALUES ('晋H12345', '4');
INSERT INTO `vehicle_usage_count` VALUES ('晋I12345', '4');
INSERT INTO `vehicle_usage_count` VALUES ('晋J12345', '5');
INSERT INTO `vehicle_usage_count` VALUES ('沪B67890', '5');
INSERT INTO `vehicle_usage_count` VALUES ('粤C54321', '1');
