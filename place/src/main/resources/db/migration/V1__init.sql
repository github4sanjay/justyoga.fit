CREATE TABLE `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `administrative_area_level_1` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `country_id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `administrative_area_level_1_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `country_id` binary(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKaeo688fwq968x2ibsi4fojmd2` (`rev`),
  CONSTRAINT `FKaeo688fwq968x2ibsi4fojmd2` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `country` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `country_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKi8n11kb79v00v7kbteggktc8` (`rev`),
  CONSTRAINT `FKi8n11kb79v00v7kbteggktc8` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `locality` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `administrative_area_level_1_id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `locality_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `administrative_area_level_1_id` binary(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKfabaymxo3v376nd3bmkabvktn` (`rev`),
  CONSTRAINT `FKfabaymxo3v376nd3bmkabvktn` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sub_localities_level_1` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `locality_id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sub_localities_level_1_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `locality_id` binary(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FK9attfsm5pwqn6u9txwhdlvs7` (`rev`),
  CONSTRAINT `FK9attfsm5pwqn6u9txwhdlvs7` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sub_localities_level_2` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` binary(16) DEFAULT NULL,
  `modified_by` binary(16) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `sub_locality_level_1_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sub_localities_level_2_aud` (
  `id` binary(255) NOT NULL,
  `rev` int NOT NULL,
  `revtype` tinyint DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` binary(255) DEFAULT NULL,
  `modified_by` binary(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sub_locality_level_1_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FKkurt4lfpxpa4wx2s1746dn0na` (`rev`),
  CONSTRAINT `FKkurt4lfpxpa4wx2s1746dn0na` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;