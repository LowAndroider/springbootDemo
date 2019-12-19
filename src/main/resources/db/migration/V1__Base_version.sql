CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `permissions` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;