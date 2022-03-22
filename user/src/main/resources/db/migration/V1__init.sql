CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
  `id` bigint NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles_aud` (
  `id` bigint NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKt0mnl3rej2p0h9gxnbalf2kdd` (`rev`),
  CONSTRAINT `FKt0mnl3rej2p0h9gxnbalf2kdd` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `cover_pic` longtext,
  `description` longtext,
  `email` varchar(255) NOT NULL,
  `is_email_verified` bit(1) DEFAULT NULL,
  `firebase_uid` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `profile_pic` longtext,
  `provider_id` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `cover_pic` longtext,
  `description` longtext,
  `email` varchar(255) DEFAULT NULL,
  `is_email_verified` bit(1) DEFAULT NULL,
  `firebase_uid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `profile_pic` longtext,
  `provider_id` longtext,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKc4vk4tui2la36415jpgm9leoq` (`rev`),
  CONSTRAINT `FKc4vk4tui2la36415jpgm9leoq` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_authorities` (
  `user_id` binary(16) NOT NULL,
  `authorities_id` bigint NOT NULL,
  KEY `FK40fukc61kvbvpc2rhv01q1g2l` (`authorities_id`),
  KEY `FKq3lq694rr66e6kpo2h84ad92q` (`user_id`),
  CONSTRAINT `FK40fukc61kvbvpc2rhv01q1g2l` FOREIGN KEY (`authorities_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKq3lq694rr66e6kpo2h84ad92q` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_authorities_aud` (
  `rev` int NOT NULL,
  `user_id` binary(255) NOT NULL,
  `authorities_id` bigint NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  PRIMARY KEY (`rev`,`user_id`,`authorities_id`),
  CONSTRAINT `FKjnjj4an4x9cq5fsb31wdrrfmy` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `hibernate_sequence` (next_val) VALUES (1);
