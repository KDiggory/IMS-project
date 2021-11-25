DROP TABLE IF EXISTS `order_items`;
 DROP TABLE IF EXISTS `orders`; 
 DROP TABLE IF EXISTS `items`;
DROP TABLE IF EXISTS `customers`;

CREATE TABLE IF NOT EXISTS `customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);



CREATE TABLE IF NOT EXISTS `items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL,
    `size` VARCHAR(40)  NOT NULL,
    `cost` INT  NOT NULL,
    PRIMARY KEY (`id`)
    
    );

    
    CREATE TABLE IF NOT EXISTS `orders` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `customerId` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (customerId) REFERENCES customers(id) 
  ON DELETE CASCADE
  ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS `order_items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `orderId` INT NOT NULL,
    `itemId` INT NOT NULL,
    `itemName` VARCHAR(40) NOT NULL,
    `numItems` INT NOT NULL,
    `cost` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (orderId) REFERENCES orders(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);