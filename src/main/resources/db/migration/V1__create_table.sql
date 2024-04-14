
CREATE TABLE IF NOT EXISTS `user` (
    `id_user` int NOT NULL AUTO_INCREMENT,
    `email` varchar(255) UNIQUE NOT NULL,
    `password` varchar(255) NOT NULL,
    `delete_flag` BOOLEAN DEFAULT FALSE,
    `role` ENUM('CUSTOMER','ADMINISTRATOR'),
    PRIMARY KEY (`id_user`)
);

CREATE TABLE IF NOT EXISTS `customer` (
    `id_customer` int NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `phone_number` VARCHAR(10),
    `address` VARCHAR(50) NOT NULL,
    `id_user` INT,
    PRIMARY KEY (`id_customer`),
    FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
);


CREATE TABLE IF NOT EXISTS `product` (
    `id_product` int NOT NULL AUTO_INCREMENT,
    `number_item` VARCHAR(255) NOT NULL UNIQUE,
    `type` ENUM ('PIZZA','PANINO','PIATTO_DI_TERRA','PIATTO_DI_MARE','BEVANDA','COCKTAIL'),
    `name_product` VARCHAR(50) NOT NULL,
    `detail_product` VARCHAR(50),
    `price` DOUBLE NOT NULL,
    `image_product` VARCHAR(50),
    `id_user` INT NOT NULL,
    PRIMARY KEY (`id_product`),
    FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
);

CREATE TABLE IF NOT EXISTS `orders` (
    `id_order` int NOT NULL AUTO_INCREMENT,
    `number_order` VARCHAR(255) NOT NULL,
    `email_customer` VARCHAR(50) NOT NULL,
    `address_customer` VARCHAR(50),
    `id_product` INT NOT NULL,
    `date_order` DATE,
    PRIMARY KEY (`id_order`),
    FOREIGN KEY (`id_product`) REFERENCES `product`(`id_product`)
);

CREATE TABLE IF NOT EXISTS `pdf` (
    `id_pdf` int NOT NULL AUTO_INCREMENT,
    `name_pdf` VARCHAR(255) NOT NULL,
    `id_order` INT NOT NULL,
    PRIMARY KEY (`id_pdf`),
    FOREIGN KEY (`id_order`) REFERENCES `orders`(`id_order`)
);




