/*
 Navicat Premium Data Transfer

 Source Server         : JavaWeb
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : yewushucang

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 07/01/2025 13:29:12
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

-- ----------------------------
-- Table structure for dim_vehicle
-- ----------------------------
DROP TABLE IF EXISTS `dim_vehicle`;
CREATE TABLE `dim_vehicle`  (
  `vehicle_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vehicle_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vehicle_code` int NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dim_vehicle
-- ----------------------------
INSERT INTO `dim_vehicle` VALUES ('京A12345', '货车', 1, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('沪B67890', '卡车', 2, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('粤C54321', '面包车', 3, 'AVAILABLE');
INSERT INTO `dim_vehicle` VALUES ('晋A12345', '货车', 4, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('晋A12345', '卡车', 6, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('晋B12345', '货车', 7, 'AVAILABLE');
INSERT INTO `dim_vehicle` VALUES ('晋C12345', '面包车', 8, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('晋D12345', '卡车', 9, 'AVAILABLE');
INSERT INTO `dim_vehicle` VALUES ('晋E12345', '小轿车', 10, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('晋F12345', '货车', 11, 'AVAILABLE');
INSERT INTO `dim_vehicle` VALUES ('晋G12345', '卡车', 12, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('晋H12345', '面包车', 13, 'AVAILABLE');
INSERT INTO `dim_vehicle` VALUES ('晋I12345', '货车', 14, 'IN_USE');
INSERT INTO `dim_vehicle` VALUES ('晋J12345', '小轿车', 15, 'AVAILABLE');

-- ----------------------------
-- Table structure for dwd_logistics_employee_summary
-- ----------------------------
DROP TABLE IF EXISTS `dwd_logistics_employee_summary`;
CREATE TABLE `dwd_logistics_employee_summary`  (
  `gender` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `employee_id` int NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `position` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` int NULL DEFAULT NULL,
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `employee_code` int NULL DEFAULT NULL,
  `order_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dwd_logistics_employee_summary
-- ----------------------------
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 1, '张三', '13800138000', '配送员', 1, 'WLDD20240104001', 1, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 13, '钱三', '13800000011', '司机', 2, 'WLDD20240104051', 28, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 20, '郭一', '13800000019', '配送员', 3, 'WLDD20240104052', 29, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 23, '周五', '13800000023', '配送员', 4, 'WLDD20240104053', 30, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 12, '郑二', '13800000010', '配送员', 5, 'WLDD20240104054', 31, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 16, '王六', '13800000014', '配送员', 6, 'WLDD20240104055', 32, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 6, '赵六', '13800000004', '配送员', 7, 'WLDD20240104056', 33, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 23, '周五', '13800000023', '配送员', 8, 'WLDD20240104057', 34, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 18, '陈八', '13800000016', '司机', 9, 'WLDD20240104058', 35, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 13, '钱三', '13800000011', '司机', 10, 'WLDD20240104059', 36, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 7, '孙七', '13800000005', '配送员', 11, 'WLDD20240104060', 37, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 11, '吴一', '13800000009', '配送员', 12, 'WLDD20240104061', 38, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 6, '赵六', '13800000004', '配送员', 13, 'WLDD20240104062', 39, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 18, '陈八', '13800000016', '司机', 14, 'WLDD20240104063', 40, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 20, '郭一', '13800000019', '配送员', 15, 'WLDD20240104064', 41, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 9, '刘九', '13800000007', '配送员', 16, 'WLDD20240104065', 42, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 19, '林九', '13800000017', '仓库管理员', 17, 'WLDD20240104066', 43, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 14, '孙四', '13800000012', '配送员', 18, 'WLDD20240104067', 44, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 7, '孙七', '13800000005', '配送员', 19, 'WLDD20240104068', 45, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 10, '陈十', '13800000008', '仓库管理员', 20, 'WLDD20240104069', 46, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 21, '黄二', '13800000020', '司机', 21, 'WLDD20240104070', 47, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 19, '林九', '13800000017', '仓库管理员', 22, 'WLDD20240104071', 48, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 23, '周五', '13800000023', '配送员', 23, 'WLDD20240104072', 49, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 8, '杨八', '13800000006', '司机', 24, 'WLDD20240104073', 50, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 24, '郑六', '13800000024', '司机', 25, 'WLDD20240104074', 51, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 15, '李五', '13800000013', '仓库管理员', 26, 'WLDD20240104075', 52, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 7, '孙七', '13800000005', '配送员', 27, 'WLDD20240104076', 53, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 17, '赵七', '13800000015', '配送员', 28, 'WLDD20240104077', 54, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 21, '黄二', '13800000020', '司机', 29, 'WLDD20240104078', 55, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 19, '林九', '13800000017', '仓库管理员', 30, 'WLDD20240104079', 56, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 17, '赵七', '13800000015', '配送员', 31, 'WLDD20240104080', 57, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 11, '吴一', '13800000009', '配送员', 32, 'WLDD20240104081', 58, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 13, '钱三', '13800000011', '司机', 33, 'WLDD20240104082', 59, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 17, '赵七', '13800000015', '配送员', 34, 'WLDD20240104083', 60, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 13, '钱三', '13800000011', '司机', 35, 'WLDD20240104084', 61, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 23, '周五', '13800000023', '配送员', 36, 'WLDD20240104085', 62, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 18, '陈八', '13800000016', '司机', 37, 'WLDD20240104086', 63, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 26, '吴九', '13800000027', '配送员', 38, 'WLDD20240104087', 64, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 26, '吴九', '13800000027', '配送员', 39, 'WLDD20240104088', 65, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 20, '郭一', '13800000019', '配送员', 40, 'WLDD20240104089', 66, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 7, '孙七', '13800000005', '配送员', 41, 'WLDD20240104090', 67, '已送达');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 8, '杨八', '13800000006', '司机', 42, 'WLDD20240104091', 68, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 21, '黄二', '13800000020', '司机', 43, 'WLDD20240104092', 69, '待发货');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 16, '王六', '13800000014', '配送员', 44, 'WLDD20240104093', 70, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 17, '赵七', '13800000015', '配送员', 45, 'WLDD20240104094', 71, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 14, '孙四', '13800000012', '配送员', 46, 'WLDD20240104095', 72, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 21, '黄二', '13800000020', '司机', 47, 'WLDD20240104096', 73, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 26, '吴九', '13800000027', '配送员', 48, 'WLDD20240104097', 74, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 21, '黄二', '13800000020', '司机', 49, 'WLDD20240104098', 75, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('MALE', 19, '林九', '13800000017', '仓库管理员', 50, 'WLDD20240104099', 76, '运输中');
INSERT INTO `dwd_logistics_employee_summary` VALUES ('FEMALE', 21, '黄二', '13800000020', '司机', 51, 'WLDD20240104100', 77, '运输中');

-- ----------------------------
-- Table structure for dwd_logistics_vehicle_summary
-- ----------------------------
DROP TABLE IF EXISTS `dwd_logistics_vehicle_summary`;
CREATE TABLE `dwd_logistics_vehicle_summary`  (
  `vehicle_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vehicle_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vehicle_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` int NULL DEFAULT NULL,
  `employee_id` int NULL DEFAULT NULL,
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `employee_code` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dwd_logistics_vehicle_summary
-- ----------------------------
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('沪B67890', '卡车', 'IN_USE', 1, 2, 'WLDD20240104001', 1);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋C12345', '面包车', 'IN_USE', 2, 8, 'WLDD20240104051', 28);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('沪B67890', '卡车', 'IN_USE', 3, 2, 'WLDD20240104052', 29);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋I12345', '货车', 'IN_USE', 4, 14, 'WLDD20240104053', 30);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋A12345', '卡车', 'IN_USE', 5, 6, 'WLDD20240104054', 31);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋J12345', '小轿车', 'AVAILABLE', 6, 15, 'WLDD20240104055', 32);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋G12345', '卡车', 'IN_USE', 7, 12, 'WLDD20240104056', 33);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('京A12345', '货车', 'IN_USE', 8, 1, 'WLDD20240104057', 34);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋D12345', '卡车', 'AVAILABLE', 9, 9, 'WLDD20240104058', 35);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋C12345', '面包车', 'IN_USE', 10, 8, 'WLDD20240104059', 36);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋E12345', '小轿车', 'IN_USE', 11, 10, 'WLDD20240104060', 37);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋E12345', '小轿车', 'IN_USE', 12, 10, 'WLDD20240104061', 38);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋A12345', '货车', 'IN_USE', 13, 4, 'WLDD20240104062', 39);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋E12345', '小轿车', 'IN_USE', 14, 10, 'WLDD20240104063', 40);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋F12345', '货车', 'AVAILABLE', 15, 11, 'WLDD20240104064', 41);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋H12345', '面包车', 'AVAILABLE', 16, 13, 'WLDD20240104065', 42);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋C12345', '面包车', 'IN_USE', 17, 8, 'WLDD20240104066', 43);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋F12345', '货车', 'AVAILABLE', 18, 11, 'WLDD20240104067', 44);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋J12345', '小轿车', 'AVAILABLE', 19, 15, 'WLDD20240104068', 45);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋D12345', '卡车', 'AVAILABLE', 20, 9, 'WLDD20240104069', 46);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋I12345', '货车', 'IN_USE', 21, 14, 'WLDD20240104070', 47);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋A12345', '货车', 'IN_USE', 22, 4, 'WLDD20240104071', 48);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('京A12345', '货车', 'IN_USE', 23, 1, 'WLDD20240104072', 49);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋A12345', '货车', 'IN_USE', 24, 4, 'WLDD20240104073', 50);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋D12345', '卡车', 'AVAILABLE', 25, 9, 'WLDD20240104074', 51);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋J12345', '小轿车', 'AVAILABLE', 26, 15, 'WLDD20240104075', 52);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋H12345', '面包车', 'AVAILABLE', 27, 13, 'WLDD20240104076', 53);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋I12345', '货车', 'IN_USE', 28, 14, 'WLDD20240104077', 54);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋F12345', '货车', 'AVAILABLE', 29, 11, 'WLDD20240104078', 55);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋E12345', '小轿车', 'IN_USE', 30, 10, 'WLDD20240104079', 56);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋I12345', '货车', 'IN_USE', 31, 14, 'WLDD20240104080', 57);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋J12345', '小轿车', 'AVAILABLE', 32, 15, 'WLDD20240104081', 58);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋F12345', '货车', 'AVAILABLE', 33, 11, 'WLDD20240104082', 59);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋J12345', '小轿车', 'AVAILABLE', 34, 15, 'WLDD20240104083', 60);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('沪B67890', '卡车', 'IN_USE', 35, 2, 'WLDD20240104084', 61);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('京A12345', '货车', 'IN_USE', 36, 1, 'WLDD20240104085', 62);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋H12345', '面包车', 'AVAILABLE', 37, 13, 'WLDD20240104086', 63);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋B12345', '货车', 'AVAILABLE', 38, 7, 'WLDD20240104087', 64);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('沪B67890', '卡车', 'IN_USE', 39, 2, 'WLDD20240104088', 65);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋F12345', '货车', 'AVAILABLE', 40, 11, 'WLDD20240104089', 66);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋H12345', '面包车', 'AVAILABLE', 41, 13, 'WLDD20240104090', 67);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋F12345', '货车', 'AVAILABLE', 42, 11, 'WLDD20240104091', 68);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋A12345', '货车', 'IN_USE', 43, 4, 'WLDD20240104092', 69);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋D12345', '卡车', 'AVAILABLE', 44, 9, 'WLDD20240104093', 70);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋G12345', '卡车', 'IN_USE', 45, 12, 'WLDD20240104094', 71);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋D12345', '卡车', 'AVAILABLE', 46, 9, 'WLDD20240104095', 72);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('沪B67890', '卡车', 'IN_USE', 47, 2, 'WLDD20240104096', 73);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('京A12345', '货车', 'IN_USE', 48, 1, 'WLDD20240104097', 74);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('粤C54321', '面包车', 'AVAILABLE', 49, 3, 'WLDD20240104098', 75);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋A12345', '卡车', 'IN_USE', 50, 6, 'WLDD20240104099', 76);
INSERT INTO `dwd_logistics_vehicle_summary` VALUES ('晋A12345', '卡车', 'IN_USE', 51, 6, 'WLDD20240104100', 77);

-- ----------------------------
-- Table structure for ods_employee
-- ----------------------------
DROP TABLE IF EXISTS `ods_employee`;
CREATE TABLE `ods_employee`  (
  `gender` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `employee_id` int NOT NULL,
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`employee_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ods_employee
-- ----------------------------
INSERT INTO `ods_employee` VALUES ('MALE', '张三', 1, '13800138000', '配送员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '李四', 2, '13800138001', '配送员');
INSERT INTO `ods_employee` VALUES ('MALE', '王五', 3, '13800138002', '仓库管理员');
INSERT INTO `ods_employee` VALUES ('MALE', '王伟', 4, '13100084729', '配送员');
INSERT INTO `ods_employee` VALUES ('MALE', '赵六', 6, '13800000004', '配送员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '孙七', 7, '13800000005', '配送员');
INSERT INTO `ods_employee` VALUES ('MALE', '杨八', 8, '13800000006', '司机');
INSERT INTO `ods_employee` VALUES ('MALE', '刘九', 9, '13800000007', '配送员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '陈十', 10, '13800000008', '仓库管理员');
INSERT INTO `ods_employee` VALUES ('MALE', '吴一', 11, '13800000009', '配送员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '郑二', 12, '13800000010', '配送员');
INSERT INTO `ods_employee` VALUES ('MALE', '钱三', 13, '13800000011', '司机');
INSERT INTO `ods_employee` VALUES ('FEMALE', '孙四', 14, '13800000012', '配送员');
INSERT INTO `ods_employee` VALUES ('MALE', '李五', 15, '13800000013', '仓库管理员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '王六', 16, '13800000014', '配送员');
INSERT INTO `ods_employee` VALUES ('MALE', '赵七', 17, '13800000015', '配送员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '陈八', 18, '13800000016', '司机');
INSERT INTO `ods_employee` VALUES ('MALE', '林九', 19, '13800000017', '仓库管理员');
INSERT INTO `ods_employee` VALUES ('MALE', '郭一', 20, '13800000019', '配送员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '黄二', 21, '13800000020', '司机');
INSERT INTO `ods_employee` VALUES ('MALE', '高三', 22, '13800000021', '仓库管理员');
INSERT INTO `ods_employee` VALUES ('MALE', '周五', 23, '13800000023', '配送员');
INSERT INTO `ods_employee` VALUES ('FEMALE', '郑六', 24, '13800000024', '司机');
INSERT INTO `ods_employee` VALUES ('MALE', '刘七', 25, '13800000025', '仓库管理员');
INSERT INTO `ods_employee` VALUES ('MALE', '吴九', 26, '13800000027', '配送员');

-- ----------------------------
-- Table structure for ods_logistics
-- ----------------------------
DROP TABLE IF EXISTS `ods_logistics`;
CREATE TABLE `ods_logistics`  (
  `order_id` int NULL DEFAULT NULL,
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `logistics_id` int NULL DEFAULT NULL,
  `vehicle_id` int NULL DEFAULT NULL,
  `employee_id` int NULL DEFAULT NULL,
  `tracking_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ods_logistics
-- ----------------------------
INSERT INTO `ods_logistics` VALUES (1, NULL, 1, 1, 2, 'WLDD20240104001', '待发货');
INSERT INTO `ods_logistics` VALUES (13, '备注51', 28, 2, 8, 'WLDD20240104051', '运输中');
INSERT INTO `ods_logistics` VALUES (20, '备注52', 29, 3, 2, 'WLDD20240104052', '待发货');
INSERT INTO `ods_logistics` VALUES (23, '备注53', 30, 4, 14, 'WLDD20240104053', '运输中');
INSERT INTO `ods_logistics` VALUES (12, '备注54', 31, 5, 6, 'WLDD20240104054', '待发货');
INSERT INTO `ods_logistics` VALUES (16, '备注55', 32, 6, 15, 'WLDD20240104055', '运输中');
INSERT INTO `ods_logistics` VALUES (6, '备注56', 33, 7, 12, 'WLDD20240104056', '已送达');
INSERT INTO `ods_logistics` VALUES (23, '备注57', 34, 8, 1, 'WLDD20240104057', '运输中');
INSERT INTO `ods_logistics` VALUES (18, '备注58', 35, 9, 9, 'WLDD20240104058', '待发货');
INSERT INTO `ods_logistics` VALUES (13, '备注59', 36, 10, 8, 'WLDD20240104059', '已送达');
INSERT INTO `ods_logistics` VALUES (7, '备注60', 37, 11, 10, 'WLDD20240104060', '运输中');
INSERT INTO `ods_logistics` VALUES (11, '备注61', 38, 12, 10, 'WLDD20240104061', '待发货');
INSERT INTO `ods_logistics` VALUES (6, '备注62', 39, 13, 4, 'WLDD20240104062', '运输中');
INSERT INTO `ods_logistics` VALUES (18, '备注63', 40, 14, 10, 'WLDD20240104063', '已送达');
INSERT INTO `ods_logistics` VALUES (20, '备注64', 41, 15, 11, 'WLDD20240104064', '运输中');
INSERT INTO `ods_logistics` VALUES (9, '备注65', 42, 16, 13, 'WLDD20240104065', '待发货');
INSERT INTO `ods_logistics` VALUES (19, '备注66', 43, 17, 8, 'WLDD20240104066', '已送达');
INSERT INTO `ods_logistics` VALUES (14, '备注67', 44, 18, 11, 'WLDD20240104067', '运输中');
INSERT INTO `ods_logistics` VALUES (7, '备注68', 45, 19, 15, 'WLDD20240104068', '待发货');
INSERT INTO `ods_logistics` VALUES (10, '备注69', 46, 20, 9, 'WLDD20240104069', '运输中');
INSERT INTO `ods_logistics` VALUES (21, '备注70', 47, 21, 14, 'WLDD20240104070', '已送达');
INSERT INTO `ods_logistics` VALUES (19, '备注71', 48, 22, 4, 'WLDD20240104071', '待发货');
INSERT INTO `ods_logistics` VALUES (23, '备注72', 49, 23, 1, 'WLDD20240104072', '运输中');
INSERT INTO `ods_logistics` VALUES (8, '备注73', 50, 24, 4, 'WLDD20240104073', '已送达');
INSERT INTO `ods_logistics` VALUES (24, '备注74', 51, 25, 9, 'WLDD20240104074', '待发货');
INSERT INTO `ods_logistics` VALUES (15, '备注75', 52, 26, 15, 'WLDD20240104075', '运输中');
INSERT INTO `ods_logistics` VALUES (7, '备注76', 53, 27, 13, 'WLDD20240104076', '已送达');
INSERT INTO `ods_logistics` VALUES (17, '备注77', 54, 28, 14, 'WLDD20240104077', '运输中');
INSERT INTO `ods_logistics` VALUES (21, '备注78', 55, 29, 11, 'WLDD20240104078', '待发货');
INSERT INTO `ods_logistics` VALUES (19, '备注79', 56, 30, 10, 'WLDD20240104079', '运输中');
INSERT INTO `ods_logistics` VALUES (17, '备注80', 57, 31, 14, 'WLDD20240104080', '已送达');
INSERT INTO `ods_logistics` VALUES (11, '备注81', 58, 32, 15, 'WLDD20240104081', '运输中');
INSERT INTO `ods_logistics` VALUES (13, '备注82', 59, 33, 11, 'WLDD20240104082', '待发货');
INSERT INTO `ods_logistics` VALUES (17, '备注83', 60, 34, 15, 'WLDD20240104083', '已送达');
INSERT INTO `ods_logistics` VALUES (13, '备注84', 61, 35, 2, 'WLDD20240104084', '运输中');
INSERT INTO `ods_logistics` VALUES (23, '备注85', 62, 36, 1, 'WLDD20240104085', '待发货');
INSERT INTO `ods_logistics` VALUES (18, '备注86', 63, 37, 13, 'WLDD20240104086', '运输中');
INSERT INTO `ods_logistics` VALUES (26, '备注87', 64, 38, 7, 'WLDD20240104087', '运输中');
INSERT INTO `ods_logistics` VALUES (26, '备注88', 65, 39, 2, 'WLDD20240104088', '待发货');
INSERT INTO `ods_logistics` VALUES (20, '备注89', 66, 40, 11, 'WLDD20240104089', '运输中');
INSERT INTO `ods_logistics` VALUES (7, '备注90', 67, 41, 13, 'WLDD20240104090', '已送达');
INSERT INTO `ods_logistics` VALUES (8, '备注91', 68, 42, 11, 'WLDD20240104091', '运输中');
INSERT INTO `ods_logistics` VALUES (21, '备注92', 69, 43, 4, 'WLDD20240104092', '待发货');
INSERT INTO `ods_logistics` VALUES (16, '备注93', 70, 44, 9, 'WLDD20240104093', '运输中');
INSERT INTO `ods_logistics` VALUES (17, '备注94', 71, 45, 12, 'WLDD20240104094', '运输中');
INSERT INTO `ods_logistics` VALUES (14, '备注95', 72, 46, 9, 'WLDD20240104095', '运输中');
INSERT INTO `ods_logistics` VALUES (21, '备注96', 73, 47, 2, 'WLDD20240104096', '运输中');
INSERT INTO `ods_logistics` VALUES (26, '备注97', 74, 48, 1, 'WLDD20240104097', '运输中');
INSERT INTO `ods_logistics` VALUES (21, '备注98', 75, 49, 3, 'WLDD20240104098', '运输中');
INSERT INTO `ods_logistics` VALUES (19, '备注99', 76, 50, 6, 'WLDD20240104099', '运输中');
INSERT INTO `ods_logistics` VALUES (21, '备注100', 77, 51, 6, 'WLDD20240104100', '运输中');

-- ----------------------------
-- Table structure for ods_orders
-- ----------------------------
DROP TABLE IF EXISTS `ods_orders`;
CREATE TABLE `ods_orders`  (
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_time` timestamp NOT NULL,
  `user_id` int NOT NULL,
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`order_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ods_orders
-- ----------------------------
INSERT INTO `ods_orders` VALUES ('北京市', 'DD20240104001', '2025-01-05 06:07:38', 1, '李四', '13900139000', '待发货');
INSERT INTO `ods_orders` VALUES ('上海市', 'DD20240104002', '2025-01-05 06:07:38', 2, '张三', '13800138000', '已发货');
INSERT INTO `ods_orders` VALUES ('广州市', 'DD20240104003', '2025-01-05 06:07:38', 3, '王五', '13700137000', '已完成');
INSERT INTO `ods_orders` VALUES ('河北省', 'ON000006', '2025-01-06 21:00:00', 7, '张伟', '13500000006', '待发货');
INSERT INTO `ods_orders` VALUES ('山东省', 'ON000007', '2025-01-07 22:00:00', 8, '李静', '13500000007', '已发货');
INSERT INTO `ods_orders` VALUES ('陕西省', 'ON000008', '2025-01-08 23:00:00', 9, '王杰', '13500000008', '已完成');
INSERT INTO `ods_orders` VALUES ('广西自治区', 'ON000009', '2025-01-10 00:00:00', 10, '陈丽', '13500000009', '已取消');
INSERT INTO `ods_orders` VALUES ('内蒙古自治区', 'ON000010', '2025-01-11 01:00:00', 11, '赵伟', '13500000010', '待发货');
INSERT INTO `ods_orders` VALUES ('河南省', 'ON000011', '2025-01-12 02:00:00', 12, '杨磊', '13500000011', '已发货');
INSERT INTO `ods_orders` VALUES ('辽宁省', 'ON000012', '2025-01-13 03:00:00', 13, '吴霞', '13500000012', '已完成');
INSERT INTO `ods_orders` VALUES ('四川省', 'ON000013', '2025-01-14 04:00:00', 14, '刘艳', '13500000013', '已取消');
INSERT INTO `ods_orders` VALUES ('吉林省', 'ON000014', '2025-01-15 05:00:00', 15, '王磊', '13500000014', '待发货');
INSERT INTO `ods_orders` VALUES ('青海省', 'ON000015', '2025-01-15 16:00:00', 16, '张欣', '13500000015', '已发货');
INSERT INTO `ods_orders` VALUES ('贵州省', 'ON000016', '2025-01-16 17:00:00', 17, '赵晨', '13500000016', '已完成');
INSERT INTO `ods_orders` VALUES ('甘肃省', 'ON000017', '2025-01-17 18:00:00', 18, '李娜', '13500000017', '已取消');
INSERT INTO `ods_orders` VALUES ('福建省', 'ON000018', '2025-01-18 19:00:00', 19, '吴宇', '13500000018', '待发货');
INSERT INTO `ods_orders` VALUES ('江西省', 'ON000019', '2025-01-19 20:00:00', 20, '孙斌', '13500000019', '已发货');
INSERT INTO `ods_orders` VALUES ('安徽省', 'ON000020', '2025-01-20 21:00:00', 21, '刘波', '13500000020', '已完成');
INSERT INTO `ods_orders` VALUES ('湖北省', 'ON000021', '2025-01-21 22:00:00', 22, '张怡', '13500000021', '已取消');
INSERT INTO `ods_orders` VALUES ('湖南省', 'ON000022', '2025-01-22 23:00:00', 23, '李丹', '13500000022', '待发货');
INSERT INTO `ods_orders` VALUES ('云南省', 'ON000023', '2025-01-24 00:00:00', 24, '王鑫', '13500000023', '已发货');
INSERT INTO `ods_orders` VALUES ('重庆市', 'ON000024', '2025-01-25 01:00:00', 25, '郑洁', '13500000024', '已完成');
INSERT INTO `ods_orders` VALUES ('北京市', 'ON000025', '2025-01-26 02:00:00', 26, '周俊', '13500000025', '已取消');
INSERT INTO `ods_orders` VALUES ('天津市', 'ON000026', '2025-01-27 03:00:00', 27, '郑思', '13500000026', '待发货');
INSERT INTO `ods_orders` VALUES ('上海市', 'ON000027', '2025-01-28 04:00:00', 28, '唐娜', '13500000027', '已发货');
INSERT INTO `ods_orders` VALUES ('浙江省', 'ON000028', '2025-01-29 05:00:00', 29, '孙明', '13500000028', '已完成');
INSERT INTO `ods_orders` VALUES ('广东省', 'ON000029', '2025-01-29 16:00:00', 30, '陈涛', '13500000029', '已取消');
INSERT INTO `ods_orders` VALUES ('江苏省', 'ON000030', '2025-01-30 17:00:00', 31, '赵玲', '13500000030', '待发货');
INSERT INTO `ods_orders` VALUES ('四川省', 'ON000031', '2025-01-31 18:00:00', 32, '李翠', '13500000031', '已发货');
INSERT INTO `ods_orders` VALUES ('山东省', 'ON000032', '2025-02-01 19:00:00', 33, '吴琳', '13500000032', '已完成');
INSERT INTO `ods_orders` VALUES ('吉林省', 'ON000033', '2025-02-02 20:00:00', 34, '黄彬', '13500000033', '已取消');
INSERT INTO `ods_orders` VALUES ('河南省', 'ON000034', '2025-02-03 21:00:00', 35, '李华', '13500000034', '待发货');
INSERT INTO `ods_orders` VALUES ('江西省', 'ON000035', '2025-02-04 22:00:00', 36, '王晨', '13500000035', '已发货');
INSERT INTO `ods_orders` VALUES ('湖北省', 'ON000036', '2025-02-05 23:00:00', 37, '周超', '13500000036', '已完成');
INSERT INTO `ods_orders` VALUES ('安徽省', 'ON000037', '2025-02-07 00:00:00', 38, '杨玥', '13500000037', '已取消');
INSERT INTO `ods_orders` VALUES ('重庆市', 'ON000038', '2025-02-08 01:00:00', 39, '陈达', '13500000038', '待发货');
INSERT INTO `ods_orders` VALUES ('青海省', 'ON000039', '2025-02-09 02:00:00', 40, '高媛', '13500000039', '已发货');
INSERT INTO `ods_orders` VALUES ('内蒙古自治区', 'ON000040', '2025-02-10 03:00:00', 41, '郑洁', '13500000040', '已完成');
INSERT INTO `ods_orders` VALUES ('云南省', 'ON000041', '2025-02-11 04:00:00', 42, '王军', '13500000041', '已取消');
INSERT INTO `ods_orders` VALUES ('新疆自治区', 'ON000042', '2025-02-11 16:00:00', 43, '李杰', '13500000042', '待发货');
INSERT INTO `ods_orders` VALUES ('广东省', 'ON000043', '2025-02-12 17:00:00', 44, '黄玲', '13500000043', '已发货');
INSERT INTO `ods_orders` VALUES ('陕西省', 'ON000044', '2025-02-13 18:00:00', 45, '赵川', '13500000044', '已完成');
INSERT INTO `ods_orders` VALUES ('浙江省', 'ON000045', '2025-02-14 19:00:00', 46, '刘浩', '13500000045', '已取消');
INSERT INTO `ods_orders` VALUES ('四川省', 'ON000046', '2025-02-15 20:00:00', 47, '张敏', '13500000046', '待发货');
INSERT INTO `ods_orders` VALUES ('山东省', 'ON000047', '2025-02-16 21:00:00', 48, '李波', '13500000047', '已发货');
INSERT INTO `ods_orders` VALUES ('河南省', 'ON000048', '2025-02-17 22:00:00', 49, '王峰', '13500000048', '已完成');
INSERT INTO `ods_orders` VALUES ('陕西省', 'ON000049', '2025-02-18 23:00:00', 50, '黄峰', '13500000049', '已取消');
INSERT INTO `ods_orders` VALUES ('福建省', 'ON000050', '2025-02-20 00:00:00', 51, '李涛', '13500000050', '待发货');
INSERT INTO `ods_orders` VALUES ('广西自治区', 'ON00100', '2025-03-01 05:00:00', 52, '刘翔', '13500000100', '已发货');
INSERT INTO `ods_orders` VALUES ('湖南省', 'ON00101', '2025-03-01 16:00:00', 53, '王波', '13500000101', '已完成');
INSERT INTO `ods_orders` VALUES ('江西省', 'ON00102', '2025-03-02 17:00:00', 54, '李莉', '13500000102', '待发货');
INSERT INTO `ods_orders` VALUES ('河北省', 'ON00103', '2025-03-03 18:00:00', 55, '张涛', '13500000103', '已发货');
INSERT INTO `ods_orders` VALUES ('辽宁省', 'ON00104', '2025-03-04 19:00:00', 56, '吴丽', '13500000104', '已完成');
INSERT INTO `ods_orders` VALUES ('重庆市', 'ON00105', '2025-03-05 20:00:00', 57, '陈龙', '13500000105', '已取消');
INSERT INTO `ods_orders` VALUES ('陕西省', 'ON00106', '2025-03-06 21:00:00', 58, '赵辉', '13500000106', '待发货');
INSERT INTO `ods_orders` VALUES ('安徽省', 'ON00107', '2025-03-07 22:00:00', 59, '刘文', '13500000107', '已发货');
INSERT INTO `ods_orders` VALUES ('天津市', 'ON00108', '2025-03-08 23:00:00', 60, '周洁', '13500000108', '已完成');
INSERT INTO `ods_orders` VALUES ('北京市', 'ON00109', '2025-03-09 23:00:00', 61, '郑国', '13500000109', '已取消');
INSERT INTO `ods_orders` VALUES ('上海市', 'ON00110', '2025-03-11 00:00:00', 62, '王杰', '13500000110', '待发货');
INSERT INTO `ods_orders` VALUES ('广东省', 'ON00111', '2025-03-12 01:00:00', 63, '李斌', '13500000111', '已发货');
INSERT INTO `ods_orders` VALUES ('河南省', 'ON00112', '2025-03-13 02:00:00', 64, '孙亮', '13500000112', '已完成');
INSERT INTO `ods_orders` VALUES ('湖北省', 'ON00113', '2025-03-14 03:00:00', 65, '张静', '13500000113', '已取消');
INSERT INTO `ods_orders` VALUES ('山西省', 'ON00114', '2025-03-15 04:00:00', 66, '刘斌', '13500000114', '待发货');
INSERT INTO `ods_orders` VALUES ('广西自治区', 'ON00115', '2025-03-15 15:00:00', 67, '王荣', '13500000115', '已发货');
INSERT INTO `ods_orders` VALUES ('浙江省', 'ON00116', '2025-03-16 16:00:00', 68, '赵丽', '13500000116', '已完成');
INSERT INTO `ods_orders` VALUES ('天津市', 'ON00117', '2025-03-17 17:00:00', 69, '李俊', '13500000117', '已取消');
INSERT INTO `ods_orders` VALUES ('四川省', 'ON00118', '2025-03-18 18:00:00', 70, '陈颖', '13500000118', '待发货');
INSERT INTO `ods_orders` VALUES ('江苏省', 'ON00119', '2025-03-19 19:00:00', 71, '黄鹏', '13500000119', '已发货');
INSERT INTO `ods_orders` VALUES ('吉林省', 'ON00120', '2025-03-20 20:00:00', 72, '周凯', '13500000120', '已完成');
INSERT INTO `ods_orders` VALUES ('重庆市', 'ON00121', '2025-03-21 21:00:00', 73, '孙佳', '13500000121', '已取消');
INSERT INTO `ods_orders` VALUES ('河南省', 'ON00122', '2025-03-22 22:00:00', 74, '王伟', '13500000122', '待发货');
INSERT INTO `ods_orders` VALUES ('北京市', 'ON00123', '2025-03-23 23:00:00', 75, '张琳', '13500000123', '已发货');
INSERT INTO `ods_orders` VALUES ('山东省', 'ON00124', '2025-03-25 00:00:00', 76, '李淑', '13500000124', '已完成');
INSERT INTO `ods_orders` VALUES ('福建省', 'ON00125', '2025-03-26 01:00:00', 77, '赵军', '13500000125', '已取消');
INSERT INTO `ods_orders` VALUES ('河北省', 'ON00126', '2025-03-27 02:00:00', 78, '吴浩', '13500000126', '待发货');
INSERT INTO `ods_orders` VALUES ('吉林省', 'ON00127', '2025-03-28 03:00:00', 79, '周芳', '13500000127', '已发货');
INSERT INTO `ods_orders` VALUES ('广东省', 'ON00128', '2025-03-29 04:00:00', 80, '郑波', '13500000128', '已完成');
INSERT INTO `ods_orders` VALUES ('四川省', 'ON00129', '2025-03-29 15:00:00', 81, '李俊', '13500000129', '已取消');
INSERT INTO `ods_orders` VALUES ('安徽省', 'ON00130', '2025-03-30 16:00:00', 82, '周敏', '13500000130', '待发货');
INSERT INTO `ods_orders` VALUES ('湖南省', 'ON00131', '2025-03-31 17:00:00', 83, '王杰', '13500000131', '已发货');
INSERT INTO `ods_orders` VALUES ('江西省', 'ON00132', '2025-04-01 18:00:00', 84, '郑磊', '13500000132', '已完成');
INSERT INTO `ods_orders` VALUES ('青海省', 'ON00133', '2025-04-02 19:00:00', 85, '张明', '13500000133', '已取消');
INSERT INTO `ods_orders` VALUES ('广东省', 'ON00134', '2025-04-03 20:00:00', 86, '李琪', '13500000134', '待发货');
INSERT INTO `ods_orders` VALUES ('甘肃省', 'ON00135', '2025-04-04 21:00:00', 87, '王浩', '13500000135', '已发货');
INSERT INTO `ods_orders` VALUES ('江苏省', 'ON00136', '2025-04-05 22:00:00', 88, '孙锋', '13500000136', '已完成');
INSERT INTO `ods_orders` VALUES ('山西省', 'ON00137', '2025-04-06 23:00:00', 89, '李光', '13500000137', '已取消');
INSERT INTO `ods_orders` VALUES ('浙江省', 'ON00138', '2025-04-08 00:00:00', 90, '赵光', '13500000138', '待发货');
INSERT INTO `ods_orders` VALUES ('福建省', 'ON00139', '2025-04-09 01:00:00', 91, '刘波', '13500000139', '已发货');
INSERT INTO `ods_orders` VALUES ('重庆市', 'ON00140', '2025-04-10 02:00:00', 92, '王鑫', '13500000140', '已完成');
INSERT INTO `ods_orders` VALUES ('四川省', 'ON00141', '2025-04-11 03:00:00', 93, '陈娟', '13500000141', '已取消');
INSERT INTO `ods_orders` VALUES ('北京市', 'ON00142', '2025-04-12 04:00:00', 94, '李娜', '13500000142', '待发货');
INSERT INTO `ods_orders` VALUES ('江西省', 'ON00143', '2025-04-12 15:00:00', 95, '周丹', '13500000143', '已发货');
INSERT INTO `ods_orders` VALUES ('河南省', 'ON00144', '2025-04-13 16:00:00', 96, '张腾', '13500000144', '已完成');
INSERT INTO `ods_orders` VALUES ('广东省', 'ON00145', '2025-04-14 17:00:00', 97, '赵雷', '13500000145', '已取消');
INSERT INTO `ods_orders` VALUES ('陕西省', 'ON00146', '2025-04-15 18:00:00', 98, '王刚', '13500000146', '待发货');
INSERT INTO `ods_orders` VALUES ('江苏省', 'ON00147', '2025-04-16 19:00:00', 99, '李康', '13500000147', '已发货');
INSERT INTO `ods_orders` VALUES ('安徽省', 'ON00148', '2025-04-17 20:00:00', 100, '郑畅', '13500000148', '已完成');
INSERT INTO `ods_orders` VALUES ('江西省', 'ON00149', '2025-04-18 21:00:00', 101, '张迪', '13500000149', '已取消');
INSERT INTO `ods_orders` VALUES ('山东省', 'ON00150', '2025-04-19 22:00:00', 102, '赵勇', '13500000150', '待发货');

SET FOREIGN_KEY_CHECKS = 1;
