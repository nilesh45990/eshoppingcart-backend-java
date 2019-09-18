create database demo;


CREATE TABLE `order_detail` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `order_status` enum('Canceled','Requested','Confirmed','In process','Dispatched','Delivered') NOT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_amount` double DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ;

CREATE TABLE `cart_detail` (
  `cart_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_quantity` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `unit_price` double NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cart_item_id`)
) ;


CREATE TABLE `customer_detail` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(30) NOT NULL,
  `customer_contact_no` varchar(10) NOT NULL,
  `customer_email` varchar(255) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_email` (`customer_email`)
) ;
INSERT INTO `customer_detail` (`customer_id`,`customer_name`,`customer_contact_no`,`customer_email`) VALUES (1,'nilesh','9426970970','nilesh45990@gmail.com');
INSERT INTO `customer_detail` (`customer_id`,`customer_name`,`customer_contact_no`,`customer_email`) VALUES (2,'tejash','9909905999','tejash@gmail.com');
INSERT INTO `customer_detail` (`customer_id`,`customer_name`,`customer_contact_no`,`customer_email`) VALUES (3,'akash','9999999999','akash@gmail.com');


CREATE TABLE `product_detail` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) NOT NULL,
  `product_price` double NOT NULL,
  `product_stock` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_id`)
) ;
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (1,'Flyer 1',10,10);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (2,'Flyer 2',20,20);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (10,'Flyer 10',100,100);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (3,'Flyer 3',30,30);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (4,'Flyer 4',40,40);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (5,'Flyer5',50,50);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (6,'Flyer 6',60,60);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (7,'Flyer 7',70,70);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (8,'Flyer 8',80,80);
INSERT INTO `product_detail` (`product_id`,`product_name`,`product_price`,`product_stock`) VALUES (9,'Flyer 9',90,90);




CREATE TABLE `app_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO app_role (id, role_name, description) VALUES (1, 'USER', 'Standard User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN', 'Admin User - Has permission to perform admin tasks');

CREATE TABLE `user_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
insert into user_login(`password`,username,role_id) values(MD5('nilesh'),'nilesh',1);

