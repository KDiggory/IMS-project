INSERT INTO `customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');

INSERT INTO `items` (`name`,`size`, `cost`) VALUES ('beer', 'very large', 2);

INSERT INTO `orders` (`customerId`) VALUES (1);

INSERT INTO `order_items` (`orderId`, `itemId`, `itemName`, `cost`, `numItems` ) VALUES (1, 1, 'beer', 2, 2);
