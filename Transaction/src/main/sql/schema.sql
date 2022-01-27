CREATE TABLE `transaction` (
    `id` bigint(20) AUTO_INCREMENT NOT NULL,
    `account_number` varchar(255) NOT NULL,
    `amount` decimal(10,2) NOT NULL,
    `type` varchar(255) NOT NULL,
    `status` varchar(255) NOT NULL,
    `last_updated` datetime NOT NULL DEFAULT current_timestamp(),
    `date_created` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;