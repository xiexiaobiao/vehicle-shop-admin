drop database vehicle_shop;
create database vehicle_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

use vehicle_shop;

drop table if exists shop_stock;
drop table if exists shop_order;
drop table if exists shop_item;
drop table if exists shop_client;
drop table if exists item_list;
drop table if exists system_user;

CREATE TABLE `shop_stock` (
  `id_stock` int(11) NOT NULL AUTO_INCREMENT,
  `item_uuid` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `quantity_locked` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_stock`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存表';


CREATE TABLE `shop_order` (
  `id_order` int(11) NOT NULL AUTO_INCREMENT,
  `order_uuid` varchar(45) DEFAULT NULL COMMENT '订单流水号',
  `client_uuid` varchar(45) DEFAULT NULL,
  `generate_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT NULL,
  `order_remark` varchar(45) DEFAULT NULL,
  `amount` decimal(8,2) DEFAULT NULL,
  `capital_amount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

CREATE TABLE `shop_item` (
  `id_item` int(11) NOT NULL AUTO_INCREMENT,
  `item_uuid` varchar(45) DEFAULT NULL,
  `classification` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL COMMENT '类别',
  `item_name` varchar(45) DEFAULT NULL,
  `sell_price` decimal(8,2) DEFAULT NULL,
  `purchase_price` decimal(8,2) DEFAULT NULL,
  `brand_name` varchar(45) DEFAULT NULL,
  `specification` varchar(45) DEFAULT NULL COMMENT '规格',
  `unit` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_shipment` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_item`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

CREATE TABLE `shop_client` (
  `id_client` int(11) NOT NULL AUTO_INCREMENT,
  `client_uuid` varchar(45) DEFAULT NULL,
  `client_name` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `vehicle_series` varchar(45) DEFAULT NULL,
  `vehicle_plate` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `addr` varchar(45) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户表';

CREATE TABLE `item_list` (
  `id_list` int(11) NOT NULL AUTO_INCREMENT,
  `order_uuid` varchar(45) DEFAULT NULL,
  `item_uuid` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `discount_price` decimal(5,2) DEFAULT NULL,
  `remark` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_list`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品明细清单';

CREATE TABLE `system_user` (
  `user_uuid` varchar(45) NOT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `user_passwd` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统管理员表';

INSERT INTO `system_user` (`user_uuid`,`user_name`,`user_passwd`) VALUES ('admin','admin','admin123');
--
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (2,'SP000011',NULL,'修理','雨刮器',60.99,45.99,'奔驰系列','35*35cm','支','奔驰系列',NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (3,'SP000012',NULL,'修理','前挡玻璃',450.00,400.00,NULL,NULL,'片',NULL,NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (4,'SP000013',NULL,'美容','打蜡',30.00,20.00,NULL,NULL,'次',NULL,NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (5,'SP000014',NULL,'美容','洗车',25.00,20.00,NULL,NULL,'次',NULL,NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (7,'SP000015',NULL,'修理','轮胎',500.00,450.00,'万力','225/45','条',NULL,NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (15,'SP000018',NULL,'修理','轮胎',450.50,400.60,'三角','245/55','条',NULL,NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (21,'SP000020',NULL,'修理','轮胎',260.00,230.00,NULL,'235/55','条',NULL,NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (22,'SP000021',NULL,'修理','轮胎',260.00,230.00,'朝阳','235/55','条',NULL,NULL);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (23,'SP000022',NULL,'保养','玻璃水',60.00,50.00,'不冻','','瓶','这个水真的水！',1);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (27,'SP000023',NULL,'美容','玻璃水',150.00,120.00,'无极','1.0升/瓶','瓶','water is water！water is water！',1);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (41,'SP000010',NULL,'修理','机油',100.00,100.00,'壳牌','1.5L','瓶','中国好品牌！',0);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (54,'SP0020',NULL,'销售','火花塞',35.00,25.00,'永久金','支','个','永久金永久金永久金',1);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (56,'SP0020',NULL,'销售','华为手机P30',125.55,120.55,'华为','14*14','台','华为华为华为华为',0);
INSERT INTO `shop_item` (`id_item`,`item_uuid`,`classification`,`category`,`item_name`,`sell_price`,`purchase_price`,`brand_name`,`specification`,`unit`,`description`,`is_shipment`) VALUES (57,'SP0025','','销售','智能手机',1200.00,1000.00,'小米','6.6寸','台','这是个好玩意这是个好玩意',0);
--
INSERT INTO `shop_client` (`id_client`,`client_uuid`,`client_name`,`age`,`gender`,`vehicle_series`,`vehicle_plate`,`phone`,`addr`,`point`) VALUES (1,'CN003','谢小标','35',1,'奔驰E','粤B254562','18613121916','深圳龙岗区爱联小区深圳龙岗区爱联小区',220);
INSERT INTO `shop_client` (`id_client`,`client_uuid`,`client_name`,`age`,`gender`,`vehicle_series`,`vehicle_plate`,`phone`,`addr`,`point`) VALUES (2,'CN002','杨女士','25',0,'BMW760','粤B223356','15036524562','深圳龙岗区龙华路',0);
INSERT INTO `shop_client` (`id_client`,`client_uuid`,`client_name`,`age`,`gender`,`vehicle_series`,`vehicle_plate`,`phone`,`addr`,`point`) VALUES (4,'CN004','YAO','18',0,'BMW740','粤A55667','1356202212','福建省厦门市思明区',1620);
INSERT INTO `shop_client` (`id_client`,`client_uuid`,`client_name`,`age`,`gender`,`vehicle_series`,`vehicle_plate`,`phone`,`addr`,`point`) VALUES (8,'CN005','谢大标','35',1,'BMW760','粤B45678','13823676088','深圳龙岗区龙石路8号',1570);
INSERT INTO `shop_client` (`id_client`,`client_uuid`,`client_name`,`age`,`gender`,`vehicle_series`,`vehicle_plate`,`phone`,`addr`,`point`) VALUES (9,'CN008','零零八','40',1,'大众A8L','闵B55555','138000138000','福建厦门湖里区万达',NULL);