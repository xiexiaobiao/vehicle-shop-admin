drop database vehicle_shop;
create database vehicle_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use vehicle_shop;

drop table if exists item_list;
CREATE TABLE `item_list` (
  `id_list` int(11) NOT NULL AUTO_INCREMENT,
  `item_uuid` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_list`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品明细清单';

drop table if exists shop_client;
CREATE TABLE `shop_client` (
  `id_client` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `vehicle_plate` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `addr` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4   COMMENT='客户表';

drop table if exists shop_item;
CREATE TABLE `shop_item` (
  `id_item` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL COMMENT '类别',
  `name` varchar(45) DEFAULT NULL,
  `sell_price` decimal(5,2) DEFAULT NULL,
  `purchase_price` decimal(5,2) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `specification` varchar(45) DEFAULT NULL COMMENT '规格',
  PRIMARY KEY (`id_item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

drop table if exists shop_order;
CREATE TABLE `shop_order` (
  `id_order` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) DEFAULT NULL COMMENT '订单流水号',
  `client_uuid` varchar(45) DEFAULT NULL,
  `id_list` int(11) DEFAULT NULL,
  `generate_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

drop table if exists shop_stock;
CREATE TABLE `shop_stock` (
  `id_stock` int(11) NOT NULL AUTO_INCREMENT,
  `item_uuid` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `quantity_locked` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_stock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='库存表';

