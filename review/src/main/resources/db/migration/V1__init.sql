CREATE TABLE `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_comments` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `review_id` binary(16) NOT NULL,
  `text` longtext NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_comments_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `review_id` binary(255) DEFAULT NULL,
  `text` longtext,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKpvsjp9vr1gkpjgkpe8nwclsqg` (`rev`),
  CONSTRAINT `FKpvsjp9vr1gkpjgkpe8nwclsqg` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_images` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `review_id` binary(16) NOT NULL,
  `url` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_images_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `review_id` binary(255) DEFAULT NULL,
  `url` longtext,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKsu463jlpp8qotwmfym5tkar6` (`rev`),
  CONSTRAINT `FKsu463jlpp8qotwmfym5tkar6` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_likes` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `review_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_likes_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `review_id` binary(255) DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FK925jqamuutwqxyhs272edes8r` (`rev`),
  CONSTRAINT `FK925jqamuutwqxyhs272edes8r` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_videos` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `review_id` binary(16) NOT NULL,
  `url` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review_videos_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `review_id` binary(255) DEFAULT NULL,
  `url` longtext,
  PRIMARY KEY (`id`,`rev`),
  KEY `FK8cpi04l6iluju3npgb9wfi392` (`rev`),
  CONSTRAINT `FK8cpi04l6iluju3npgb9wfi392` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reviews` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `is_published` bit(1) NOT NULL,
  `rating` double NOT NULL,
  `review_content` longtext,
  `review_text` longtext,
  `reviewed_by` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reviews_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_published` bit(1) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `review_content` longtext,
  `review_text` longtext,
  `reviewed_by` binary(255) DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKkqpr9pjy5wa8hacoea2s9yl7s` (`rev`),
  CONSTRAINT `FKkqpr9pjy5wa8hacoea2s9yl7s` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;