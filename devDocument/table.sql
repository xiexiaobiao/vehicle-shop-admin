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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存表';


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
  PRIMARY KEY (`id_order`),
  UNIQUE KEY `order_uuid_UNIQUE` (`order_uuid`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

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
  `alert_quantity` int(3) DEFAULT NULL,
  PRIMARY KEY (`id_item`),
  UNIQUE KEY `item_uuid_UNIQUE` (`item_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

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
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `client_uuid_UNIQUE` (`client_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户表';

CREATE TABLE `item_list` (
  `id_list` int(11) NOT NULL AUTO_INCREMENT,
  `order_uuid` varchar(45) DEFAULT NULL,
  `item_uuid` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `discount_price` decimal(8,2) DEFAULT NULL,
  `remark` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_list`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品明细清单';

CREATE TABLE `shop_item_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_uuid` varchar(45) DEFAULT NULL,
  `pic_addr` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品图片';

CREATE TABLE `system_user` (
  `user_uuid` varchar(45) NOT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `user_passwd` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统管理员表';

INSERT INTO `system_user` (`user_uuid`,`user_name`,`user_passwd`) VALUES ('admin','admin','admin123');
--
