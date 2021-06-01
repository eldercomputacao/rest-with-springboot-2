-- Adminer 4.8.0 MySQL 8.0.25 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;

SET NAMES utf8mb4;





CREATE TABLE `tb_user_permission` (
  `id_user` bigint NOT NULL,
  `id_permission` bigint NOT NULL,
  KEY `FKqsxlawmvr4dhvmhyhjmjjj3rt` (`id_permission`),
  KEY `FKt719rbex1tr7l6pwqytxmsutv` (`id_user`),
  CONSTRAINT `FKqsxlawmvr4dhvmhyhjmjjj3rt` FOREIGN KEY (`id_permission`) REFERENCES `tb_permission` (`id`),
  CONSTRAINT `FKt719rbex1tr7l6pwqytxmsutv` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 2021-05-27 17:25:10
