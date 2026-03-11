create database IF NOT EXISTS OrderManagement;

use OrderManagement;

drop table `order`;
drop table Product;	
drop table client;
drop table Log;

create table IF NOT EXISTS Client(
	id int primary key AUTO_INCREMENT,
    name VARCHAR(100),
    address VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO Client VALUES 
(0,'Alice Smith', 'example str 1', 'alice@example.com'),
(0,'Bob Johnson', 'example str 2', 'bob@example.com'),
(0,'Charlie Davis', 'example str 3', 'charlie@example.com');

create table IF NOT EXISTS Product(
	id int primary key AUTO_INCREMENT,
    name VARCHAR(100),
    stock int
);

insert into product values
(0,'Laptop',10),
(0,'Keyboard',100),
(0,'Mouse',250),
(0,'USB',50),
(0,'IPad',1);

create table IF NOT EXISTS `Order`(
	orderId int primary key auto_increment,
    clientId int,
    productId int,
    quantity int,
    foreign key(clientId) references Client(id) ON DELETE CASCADE,
    foreign key(productId) references Product(id) ON DELETE CASCADE
);

create table IF NOT EXISTS Log(
	orderId int,
    clientName VARCHAR(100),
    productName VARCHAR(100),
    quantity int,
    `date`	timestamp DEFAULT CURRENT_TIMESTAMP
);