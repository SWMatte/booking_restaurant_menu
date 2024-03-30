CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `delete_flag` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id_user`)
);