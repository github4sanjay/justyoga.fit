CREATE VIEW `search`.`collection_image_info` AS
 SELECT
 `ci`.`collection_id` AS `collection_id`,
  `i`.`id` AS `id`,
  `i`.`created_at` AS `created_at`,
  `i`.`created_by` AS `created_by`,
  `i`.`modified_by` AS `modified_by`,
  `i`.`updated_at` AS `updated_at`,
  `i`.`description` AS `description`,
  `i`.`title` AS `title`,
  `i`.`url`  AS `url`,
  `i`.`user_id` AS `user_id`,
   `u`.`email` AS `email`,
   `u`.`name` AS `name`,
   `u`.`profile_pic` AS `profile_pic`,
   `u`.`is_email_verified` AS `is_email_verified`,
   `u`.`cover_pic` AS `user_cover_pic`,
   `u`.`phone_number` AS `phone_number`,
   `u`.`provider_id` AS `provider_id`,
   `u`.`firebase_uid` AS `firebase_uid`,
   `u`.`description` AS `user_description`,
   `pbi`.`administrative_area_level1_id` AS `administrative_area_level1_id`,
   `pbi`.`sub_locality_level1_id` AS `sub_locality_level1_id`,
   `pbi`.`sub_locality_level2_id` AS `sub_locality_level2_id`,
   `pbi`.`locality_id` AS `locality_id`,
   `pbi`.`country_id` AS `country_id`,
   `pbi`.`formatted_address` AS `formatted_address`,
   `pbi`.`geohash_1` AS `geohash_1`,
   `pbi`.`geohash_150` AS `geohash_150`,
   `pbi`.`geohash_5` AS `geohash_5`,
   `pbi`.`geohash_50` AS `geohash_50`,
   `pbi`.`latitude` AS `latitude`,
   `pbi`.`longitude` AS `longitude`,
   `pbi`.`postal_code` AS `postal_code`,
   `pbi`.`is_active` AS `is_active`,
   `pbi`.`age` AS `age`,
   `pbi`.`experience_months` AS `experience_months`,
   `pbi`.`experience_years` AS `experience_years`,
   `pui`.`is_trainer` AS `is_trainer`,
   `pui`.`is_looking_for_trainer` AS `is_looking_for_trainer`,
   `pui`.`is_blogger` AS `is_blogger`,
    AVG(`r`.`rating`) as `rating`,
    COUNT(distinct `r`.`id`) as `review_count`
FROM
`collection`.`collection_images` `ci`
left outer join `profile`.`images` `i` on `ci`.`image_id` = `i`.id
left outer join `user`.`users` `u` on `u`.`id` = `i`.`user_id`
left outer join `profile`.`user_basic_info` `pbi` on `i`.`user_id` = `pbi`.`user_id`
left outer join `profile`.`interests` `pui` on `i`.`user_id` = `pui`.`user_id`
left outer join `review`.`reviews` `r` on `i`.`user_id` = `r`.`user_id`
group by `ci`.`id`;


CREATE VIEW `search`.`collection_video_info` AS
 SELECT
 `ci`.`collection_id` AS `collection_id`,
  `i`.`id` AS `id`,
  `i`.`created_at` AS `created_at`,
  `i`.`created_by` AS `created_by`,
  `i`.`modified_by` AS `modified_by`,
  `i`.`updated_at` AS `updated_at`,
  `i`.`description` AS `description`,
  `i`.`title` AS `title`,
  `i`.`url`  AS `url`,
  `i`.`cover_pic`  AS `cover_pic`,
  `i`.`user_id` AS `user_id`,
   `u`.`email` AS `email`,
   `u`.`name` AS `name`,
   `u`.`profile_pic` AS `profile_pic`,
   `u`.`is_email_verified` AS `is_email_verified`,
   `u`.`cover_pic` AS `user_cover_pic`,
   `u`.`phone_number` AS `phone_number`,
   `u`.`provider_id` AS `provider_id`,
   `u`.`firebase_uid` AS `firebase_uid`,
   `u`.`description` AS `user_description`,
   `pbi`.`administrative_area_level1_id` AS `administrative_area_level1_id`,
   `pbi`.`sub_locality_level1_id` AS `sub_locality_level1_id`,
   `pbi`.`sub_locality_level2_id` AS `sub_locality_level2_id`,
   `pbi`.`locality_id` AS `locality_id`,
   `pbi`.`country_id` AS `country_id`,
   `pbi`.`formatted_address` AS `formatted_address`,
   `pbi`.`geohash_1` AS `geohash_1`,
   `pbi`.`geohash_150` AS `geohash_150`,
   `pbi`.`geohash_5` AS `geohash_5`,
   `pbi`.`geohash_50` AS `geohash_50`,
   `pbi`.`latitude` AS `latitude`,
   `pbi`.`longitude` AS `longitude`,
   `pbi`.`postal_code` AS `postal_code`,
   `pbi`.`is_active` AS `is_active`,
   `pbi`.`age` AS `age`,
   `pbi`.`experience_months` AS `experience_months`,
   `pbi`.`experience_years` AS `experience_years`,
   `pui`.`is_trainer` AS `is_trainer`,
   `pui`.`is_looking_for_trainer` AS `is_looking_for_trainer`,
   `pui`.`is_blogger` AS `is_blogger`,
    AVG(`r`.`rating`) as `user_rating`,
    COUNT(distinct `r`.`id`) as `user_review_count`
FROM
`collection`.`collection_videos` `ci`
left outer join `profile`.`videos` `i` on `ci`.`video_id` = `i`.id
left outer join `user`.`users` `u` on `u`.`id` = `i`.`user_id`
left outer join `profile`.`user_basic_info` `pbi` on `i`.`user_id` = `pbi`.`user_id`
left outer join `profile`.`interests` `pui` on `i`.`user_id` = `pui`.`user_id`
left outer join `review`.`reviews` `r` on `i`.`user_id` = `r`.`user_id`
group by `ci`.`id`;