/*
 Navicat Premium Data Transfer

 Source Server         : JavaWeb
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : yewuads

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 06/01/2025 15:19:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ads_employee_order_count
-- ----------------------------
DROP TABLE IF EXISTS `ads_employee_order_count`;
CREATE TABLE `ads_employee_order_count`  (
  `id` int NULL DEFAULT NULL,
  `employee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ads_employee_order_count
-- ----------------------------
INSERT INTO `ads_employee_order_count` VALUES (1, '刘九');
INSERT INTO `ads_employee_order_count` VALUES (2, '吴一');
INSERT INTO `ads_employee_order_count` VALUES (3, '吴九');
INSERT INTO `ads_employee_order_count` VALUES (4, '周五');
INSERT INTO `ads_employee_order_count` VALUES (4, '孙七');
INSERT INTO `ads_employee_order_count` VALUES (2, '孙四');
INSERT INTO `ads_employee_order_count` VALUES (1, '张三');
INSERT INTO `ads_employee_order_count` VALUES (1, '李五');
INSERT INTO `ads_employee_order_count` VALUES (2, '杨八');
INSERT INTO `ads_employee_order_count` VALUES (4, '林九');
INSERT INTO `ads_employee_order_count` VALUES (2, '王六');
INSERT INTO `ads_employee_order_count` VALUES (4, '赵七');
INSERT INTO `ads_employee_order_count` VALUES (2, '赵六');
INSERT INTO `ads_employee_order_count` VALUES (1, '郑二');
INSERT INTO `ads_employee_order_count` VALUES (1, '郑六');
INSERT INTO `ads_employee_order_count` VALUES (3, '郭一');
INSERT INTO `ads_employee_order_count` VALUES (4, '钱三');
INSERT INTO `ads_employee_order_count` VALUES (3, '陈八');
INSERT INTO `ads_employee_order_count` VALUES (1, '陈十');
INSERT INTO `ads_employee_order_count` VALUES (6, '黄二');

-- ----------------------------
-- Table structure for ads_logistics_status_summary
-- ----------------------------
DROP TABLE IF EXISTS `ads_logistics_status_summary`;
CREATE TABLE `ads_logistics_status_summary`  (
  `id` int NULL DEFAULT NULL,
  `status_description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ads_logistics_status_summary
-- ----------------------------
INSERT INTO `ads_logistics_status_summary` VALUES (10, '已送达');
INSERT INTO `ads_logistics_status_summary` VALUES (14, '待发货');
INSERT INTO `ads_logistics_status_summary` VALUES (27, '运输中');

-- ----------------------------
-- Table structure for ads_province_order_summary
-- ----------------------------
DROP TABLE IF EXISTS `ads_province_order_summary`;
CREATE TABLE `ads_province_order_summary`  (
  `province_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_count` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ads_province_order_summary
-- ----------------------------
INSERT INTO `ads_province_order_summary` VALUES ('上海市', 3);
INSERT INTO `ads_province_order_summary` VALUES ('云南省', 2);
INSERT INTO `ads_province_order_summary` VALUES ('内蒙古自治区', 2);
INSERT INTO `ads_province_order_summary` VALUES ('北京市', 5);
INSERT INTO `ads_province_order_summary` VALUES ('吉林省', 4);
INSERT INTO `ads_province_order_summary` VALUES ('四川省', 6);
INSERT INTO `ads_province_order_summary` VALUES ('天津市', 3);
INSERT INTO `ads_province_order_summary` VALUES ('安徽省', 5);
INSERT INTO `ads_province_order_summary` VALUES ('山东省', 5);
INSERT INTO `ads_province_order_summary` VALUES ('山西省', 2);
INSERT INTO `ads_province_order_summary` VALUES ('广东省', 6);
INSERT INTO `ads_province_order_summary` VALUES ('广州市', 1);
INSERT INTO `ads_province_order_summary` VALUES ('广西自治区', 3);
INSERT INTO `ads_province_order_summary` VALUES ('新疆自治区', 1);
INSERT INTO `ads_province_order_summary` VALUES ('江苏省', 4);
INSERT INTO `ads_province_order_summary` VALUES ('江西省', 6);
INSERT INTO `ads_province_order_summary` VALUES ('河北省', 3);
INSERT INTO `ads_province_order_summary` VALUES ('河南省', 6);
INSERT INTO `ads_province_order_summary` VALUES ('浙江省', 4);
INSERT INTO `ads_province_order_summary` VALUES ('湖北省', 3);
INSERT INTO `ads_province_order_summary` VALUES ('湖南省', 3);
INSERT INTO `ads_province_order_summary` VALUES ('甘肃省', 2);
INSERT INTO `ads_province_order_summary` VALUES ('福建省', 4);
INSERT INTO `ads_province_order_summary` VALUES ('贵州省', 1);
INSERT INTO `ads_province_order_summary` VALUES ('辽宁省', 2);
INSERT INTO `ads_province_order_summary` VALUES ('重庆市', 5);
INSERT INTO `ads_province_order_summary` VALUES ('陕西省', 5);
INSERT INTO `ads_province_order_summary` VALUES ('青海省', 3);

-- ----------------------------
-- Table structure for ads_vehicle_usage_count
-- ----------------------------
DROP TABLE IF EXISTS `ads_vehicle_usage_count`;
CREATE TABLE `ads_vehicle_usage_count`  (
  `id` int NULL DEFAULT NULL,
  `vehicle_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ads_vehicle_usage_count
-- ----------------------------
INSERT INTO `ads_vehicle_usage_count` VALUES (4, '京A12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (7, '晋A12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (1, '晋B12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (3, '晋C12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (5, '晋D12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (4, '晋E12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (6, '晋F12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (2, '晋G12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (4, '晋H12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (4, '晋I12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (5, '晋J12345');
INSERT INTO `ads_vehicle_usage_count` VALUES (5, '沪B67890');
INSERT INTO `ads_vehicle_usage_count` VALUES (1, '粤C54321');

SET FOREIGN_KEY_CHECKS = 1;
