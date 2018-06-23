CREATE DATABASE `ds` /*!40100 COLLATE 'utf8_general_ci' */;
CREATE DATABASE `ds_0` /*!40100 COLLATE 'utf8_general_ci' */;
CREATE DATABASE `ds_1` /*!40100 COLLATE 'utf8_general_ci' */;


drop table `s_system_log`;
CREATE TABLE `s_system_log` (
	`ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`ACCOUNT` VARCHAR(100) NULL DEFAULT NULL,
	`OPERATION` VARCHAR(255) NOT NULL,
	`OPERATION_DETAIL` TEXT NULL,
	`OPERATION_TIME` DATETIME NULL DEFAULT NULL,
	`TOKEN` VARCHAR(40) NULL DEFAULT NULL,
	`IP_ADDRESS` VARCHAR(40) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)
;

-- insert into s_system_log(OPERATION) values('/test');
-- select * from s_system_log;
CREATE TABLE IF NOT EXISTS t_product (
  id  INT NOT NULL,
  name VARCHAR(255),
  price INT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_user (
  id  INT NOT NULL,
  name VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_shop (
  id  INT NOT NULL,
  name VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_order_0 (
  order_id INT NOT NULL,
  user_id  INT NOT NULL,
  user_name  VARCHAR(255),
  PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS t_order_item_0 (
  item_id  INT NOT NULL AUTO_INCREMENT,
  order_id INT NOT NULL,
  user_id  INT NOT NULL,
  item_name VARCHAR(255),
  PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS t_shop_order_0 (
  order_id INT NOT NULL,
  shop_id  INT NOT NULL,
  shop_name  VARCHAR(255),
  PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS t_shop_order_item_0 (
  item_id  INT NOT NULL AUTO_INCREMENT,
  order_id INT NOT NULL,
  shop_id  INT NOT NULL,
  item_name VARCHAR(255),
  PRIMARY KEY (item_id)
);

