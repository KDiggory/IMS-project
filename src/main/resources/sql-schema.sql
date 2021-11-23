CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL,
    `size` VARCHAR(40)  NOT NULL,
    `cost` INT  NOT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `ims`.`orders` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `customerId` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (customerId) REFERENCES customers(id) 
  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `ims`.`order_items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `orderId` INT NOT NULL,
    `itemId` INT NOT NULL,
    `itemName` VARCHAR(40) NOT NULL,
    `numItems` INT NOT NULL,
    `cost` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (orderId) REFERENCES orders(id)
  ON DELETE CASCADE
);



