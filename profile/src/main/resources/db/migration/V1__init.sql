CREATE TABLE `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `images` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `description` longtext NOT NULL,
  `title` longtext NOT NULL,
  `url` longtext NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `images_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` longtext,
  `title` longtext,
  `url` longtext,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKr9desky9tki33l7mjbutymycx` (`rev`),
  CONSTRAINT `FKr9desky9tki33l7mjbutymycx` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `interests` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `is_blogger` bit(1) NOT NULL,
  `is_looking_for_trainer` bit(1) NOT NULL,
  `is_trainer` bit(1) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f16fuxwo1vm61cg21wwmr2i0f` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `interests_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_blogger` bit(1) DEFAULT NULL,
  `is_looking_for_trainer` bit(1) DEFAULT NULL,
  `is_trainer` bit(1) DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKmb7rb48bck62lq7u9ggn8m1nv` (`rev`),
  CONSTRAINT `FKmb7rb48bck62lq7u9ggn8m1nv` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `medical_expertise` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `description` varchar(500) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nlygqus2fge7orwadc2gk193j` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `medical_expertise_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKre8wksoyypxl42klc7f3f06dq` (`rev`),
  CONSTRAINT `FKre8wksoyypxl42klc7f3f06dq` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_basic_info` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `administrative_area_level1_id` binary(16) NOT NULL,
  `country_id` binary(16) NOT NULL,
  `formatted_address` varchar(255) NOT NULL,
  `geohash_1` varchar(255) NOT NULL,
  `geohash_150` varchar(255) NOT NULL,
  `geohash_5` varchar(255) NOT NULL,
  `geohash_50` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `locality_id` binary(16) NOT NULL,
  `longitude` double NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `sub_locality_level1_id` binary(16) NOT NULL,
  `sub_locality_level2_id` binary(16) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `age` bigint NOT NULL,
  `experience_months` bigint NOT NULL,
  `experience_years` bigint NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1bkei2lb7pya6n12d3xn710cc` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_basic_info_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `administrative_area_level1_id` binary(255) DEFAULT NULL,
  `country_id` binary(255) DEFAULT NULL,
  `formatted_address` varchar(255) DEFAULT NULL,
  `geohash_1` varchar(255) DEFAULT NULL,
  `geohash_150` varchar(255) DEFAULT NULL,
  `geohash_5` varchar(255) DEFAULT NULL,
  `geohash_50` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `locality_id` binary(255) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `sub_locality_level1_id` binary(255) DEFAULT NULL,
  `sub_locality_level2_id` binary(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `age` bigint DEFAULT NULL,
  `experience_months` bigint DEFAULT NULL,
  `experience_years` bigint DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKkcb0xn5x9vr6up2bq1rxliuk6` (`rev`),
  CONSTRAINT `FKkcb0xn5x9vr6up2bq1rxliuk6` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_medical_expertise` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `medical_expertise_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_medical_expertise_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `medical_expertise_id` binary(255) DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKlne3uqv3yf1dx09hufm7hcfs2` (`rev`),
  CONSTRAINT `FKlne3uqv3yf1dx09hufm7hcfs2` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_yoga_certificate` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` binary(16) NOT NULL,
  `yoga_certificate_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_yoga_certificate_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  `yoga_certificate_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKdtme3ldgj95h1ex052syudxt1` (`rev`),
  CONSTRAINT `FKdtme3ldgj95h1ex052syudxt1` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_yoga_expertise` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` binary(16) NOT NULL,
  `yoga_expertise_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_yoga_expertise_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  `yoga_expertise_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKr4rxdhg8fkn42gb4d0a3dgmp1` (`rev`),
  CONSTRAINT `FKr4rxdhg8fkn42gb4d0a3dgmp1` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `videos` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `cover_pic` longtext NOT NULL,
  `description` longtext NOT NULL,
  `duration` varchar(255) NOT NULL,
  `title` longtext NOT NULL,
  `url` longtext NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `videos_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `cover_pic` longtext,
  `description` longtext,
  `duration` varchar(255) DEFAULT NULL,
  `title` longtext,
  `url` longtext,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKlu1qywxmye7nixoefupksuj39` (`rev`),
  CONSTRAINT `FKlu1qywxmye7nixoefupksuj39` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `yoga_certificates` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `description` varchar(500) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `yoga_certificates_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FK1edsw6jquekw3xjrr0w1rxucx` (`rev`),
  CONSTRAINT `FK1edsw6jquekw3xjrr0w1rxucx` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `yoga_expertise` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `description` varchar(500) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `yoga_expertise_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKeyfvdr4oafgqigy4fjrw82v2b` (`rev`),
  CONSTRAINT `FKeyfvdr4oafgqigy4fjrw82v2b` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;