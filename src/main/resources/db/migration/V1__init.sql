CREATE TABLE `t_orders`
(
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_number` VARCHAR(255) DEFAULT NULL,
    `sku_code` VARCHAR(255),
    `price` DECIMAL(19, 2),
    `quantity` int(11),
    `order_date` DATE
);