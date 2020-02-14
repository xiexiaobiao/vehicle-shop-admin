drop database vehicle_shop;
create database vehicle_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

use vehicle_shop;

drop table if exists item_list;
CREATE TABLE `item_list` (
  `id_list` int(11) NOT NULL AUTO_INCREMENT,
  `order_uuid` varchar(45) DEFAULT NULL,
  `item_uuid` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `remark` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_list`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品明细清单'

CREATE TABLE `shop_client` (
  `id_client` int(11) NOT NULL AUTO_INCREMENT,
  `client_uuid` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `vehicle_plate` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `addr` varchar(45) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户表'

CREATE TABLE `shop_item` (
  `id_item` int(11) NOT NULL AUTO_INCREMENT,
  `item_uuid` varchar(45) DEFAULT NULL,
  `classification` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL COMMENT '类别',
  `item_name` varchar(45) DEFAULT NULL,
  `sell_price` decimal(5,2) DEFAULT NULL,
  `purchase_price` decimal(5,2) DEFAULT NULL,
  `brand_name` varchar(45) DEFAULT NULL,
  `specification` varchar(45) DEFAULT NULL COMMENT '规格',
  `description` varchar(200) DEFAULT NULL,
  `is_shipment` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_item`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表'

CREATE TABLE `shop_order` (
  `id_order` int(11) NOT NULL AUTO_INCREMENT,
  `order_uuid` varchar(45) DEFAULT NULL COMMENT '订单流水号',
  `client_uuid` varchar(45) DEFAULT NULL,
  `generate_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表'

CREATE TABLE `shop_stock` (
  `id_stock` int(11) NOT NULL AUTO_INCREMENT,
  `item_uuid` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `quantity_locked` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_stock`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存表'

INSERT INTO `vehicle_shop`.`shop_item` (`uuid`, `category`, `name`, `sell_price`, `purchase_price`, `brand`, `specification`) VALUES ('SP000010', '修理', '机油', '150.00', '100.00', '壳牌', '1.5L');
INSERT INTO `vehicle_shop`.`shop_item` (`uuid`, `category`, `name`, `sell_price`, `purchase_price`, `specification`) VALUES ('SP000011', '修理', '雨刮器', '60.00', '45.00', '35cm');
INSERT INTO `vehicle_shop`.`shop_item` (`uuid`, `category`, `name`, `sell_price`, `purchase_price`) VALUES ('SP000012', '修理', '前挡玻璃', '450.00', '400.00');
INSERT INTO `vehicle_shop`.`shop_item` (`uuid`, `category`, `name`, `sell_price`, `purchase_price`) VALUES ('SP000013', '美容', '打蜡', '30.00', '20.00');
INSERT INTO `vehicle_shop`.`shop_item` (`uuid`, `category`, `name`, `sell_price`, `purchase_price`) VALUES ('SP000014', '美容', '洗车', '25.00', '20.00');
INSERT INTO `vehicle_shop`.`shop_item` (`uuid`, `category`, `name`, `sell_price`, `purchase_price`) VALUES ('SP000015', '美容', '补胎', '25.00', '20.00');
INSERT INTO `vehicle_shop`.`shop_item` (`uuid`, `category`, `name`, `sell_price`, `purchase_price`, `brand`, `specification`) VALUES ('SP000016', '修理', '轮胎', '500.00', '450.00', '万力', '225/45');

INSERT INTO `vehicle_shop`.`shop_client` (`uuid`, `name`, `age`, `gender`, `vehicle_plate`, `phone`, `addr`) VALUES ('CN003', '谢先生', '35', '1', '粤B254562', '13865642563', '深圳龙岗区爱联小区');
INSERT INTO `vehicle_shop`.`shop_client` (`uuid`, `name`, `age`, `gender`, `vehicle_plate`, `phone`, `addr`) VALUES ('CN002', '马女士', '25', '0', '粤B223356', '15036524562', '深圳龙岗区龙华路');


INSERT INTO `vehicle_shop`.`shop_stock` (`item_uuid`, `quantity`, `quantity_locked`) VALUES ('SP000010', '100', '10');
INSERT INTO `vehicle_shop`.`shop_stock` (`item_uuid`, `quantity`, `quantity_locked`) VALUES ('SP000011', '100', '10');
INSERT INTO `vehicle_shop`.`shop_stock` (`item_uuid`, `quantity`, `quantity_locked`) VALUES ('SP000012', '100', '10');
INSERT INTO `vehicle_shop`.`shop_stock` (`item_uuid`, `quantity`, `quantity_locked`) VALUES ('SP000013', '100', '10');
INSERT INTO `vehicle_shop`.`shop_stock` (`item_uuid`, `quantity`, `quantity_locked`) VALUES ('SP000014', '100', '10');
INSERT INTO `vehicle_shop`.`shop_stock` (`item_uuid`, `quantity`, `quantity_locked`) VALUES ('SP000015', '100', '10');
