INSERT INTO `ims`.`customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');

INSERT INTO `items` (`name`,`size`, `cost`) VALUES ('beer', 'very large', 5);

INSERT INTO `ims`.`orders` (`customerId`) VALUES (1);

INSERT INTO `ims`.`order_items` (`orderId`, `itemId`, `itemName`, `cost`, `numItems` ) VALUES (1,1, ' beer', 5, 2);
