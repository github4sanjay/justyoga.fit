CREATE TABLE `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collections` (
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
  `url` longtext,
  `description` longtext,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collections_aud` (
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
  `url` longtext,
  `description` longtext,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKdolsyd2jm5vtcs5k1xhyu1rvx` (`rev`),
  CONSTRAINT `FKdolsyd2jm5vtcs5k1xhyu1rvx` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collection_blogs` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `blog_id` binary(16) NOT NULL,
  `collection_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collection_blogs_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `blog_id` binary(255) DEFAULT NULL,
  `collection_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKe7v5f2ub9u74ssfo4uwou49a5` (`rev`),
  CONSTRAINT `FKe7v5f2ub9u74ssfo4uwou49a5` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collection_images` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `collection_id` binary(16) NOT NULL,
  `image_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collection_images_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `collection_id` binary(255) DEFAULT NULL,
  `image_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKfm1ikf1653mih2n369qc4qol` (`rev`),
  CONSTRAINT `FKfm1ikf1653mih2n369qc4qol` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collection_videos` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `collection_id` binary(16) NOT NULL,
  `video_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `collection_videos_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `collection_id` binary(255) DEFAULT NULL,
  `video_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FK8iegbw7c3mddnf46ki3o8a1m3` (`rev`),
  CONSTRAINT `FK8iegbw7c3mddnf46ki3o8a1m3` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;