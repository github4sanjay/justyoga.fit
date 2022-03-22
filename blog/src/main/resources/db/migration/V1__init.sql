CREATE TABLE `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blogs` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `blog_content` longtext,
  `blog_text` longtext,
  `blog_title` longtext,
  `is_published` bit(1) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `cover_url` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blogs_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `blog_content` longtext,
  `blog_text` longtext,
  `blog_title` longtext,
  `is_published` bit(1) DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  `cover_url` longtext,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKaay6bgf6bneusp1hj1gabqqo7` (`rev`),
  CONSTRAINT `FKaay6bgf6bneusp1hj1gabqqo7` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `blog_comments` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `blog_id` binary(16) NOT NULL,
  `text` longtext NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blog_comments_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `blog_id` binary(255) DEFAULT NULL,
  `text` longtext,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKbw9dbh6am1kr5lkjmxlj2kfaj` (`rev`),
  CONSTRAINT `FKbw9dbh6am1kr5lkjmxlj2kfaj` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blog_images` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `blog_id` binary(16) NOT NULL,
  `url` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blog_images_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `blog_id` binary(255) DEFAULT NULL,
  `url` longtext,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKq70ui204vrjk7skywwn40i51q` (`rev`),
  CONSTRAINT `FKq70ui204vrjk7skywwn40i51q` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blog_likes` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `blog_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blog_likes_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `blog_id` binary(255) DEFAULT NULL,
  `user_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKtfiej9yt0n4gu2b25hid5q6lg` (`rev`),
  CONSTRAINT `FKtfiej9yt0n4gu2b25hid5q6lg` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blog_videos` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `blog_id` binary(16) NOT NULL,
  `url` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blog_videos_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `blog_id` binary(255) DEFAULT NULL,
  `url` longtext,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKrf0l4pjm674vs8gb9ee4svg7s` (`rev`),
  CONSTRAINT `FKrf0l4pjm674vs8gb9ee4svg7s` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

