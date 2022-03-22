create view `search`.`user_info` AS
 select
   `u`.`id` AS `id`,
   `u`.`email` AS `email`,
   `u`.`name` AS `name`,
   `u`.`profile_pic` AS `profile_pic`,
   `u`.`is_email_verified` AS `is_email_verified`,
   `u`.`cover_pic` AS `cover_pic`,
   `u`.`phone_number` AS `phone_number`,
   `u`.`provider_id` AS `provider_id`,
   `u`.`firebase_uid` AS `firebase_uid`,
   `u`.`description` AS `description`,
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
    COUNT(distinct `r`.`id`) as `review_count`,
    GROUP_CONCAT(distinct (select name from `profile`.`medical_expertise` where `id` = `ume`.`medical_expertise_id`) SEPARATOR '|') as `user_medical_expertise`,
    GROUP_CONCAT(distinct (select name from `profile`.`yoga_certificates` where `id` = `uyc`.`yoga_certificate_id`) SEPARATOR '|') as `user_yoga_certificate`,
    GROUP_CONCAT(distinct (select name from `profile`.`yoga_expertise` where `id` = `uye`.`yoga_expertise_id`) SEPARATOR '|') as `user_yoga_expertise`
FROM
`user`.`users` `u`
left outer join `profile`.`user_basic_info` `pbi` on `u`.`id` = `pbi`.`user_id`
left outer join `profile`.`interests` `pui` on `u`.`id` = `pui`.`user_id`
left outer join `review`.`reviews` `r` on `u`.`id` = `r`.`user_id`
left outer join `profile`.`user_medical_expertise` `ume` on `u`.`id` = `ume`.`user_id`
left outer join `profile`.`user_yoga_certificate` `uyc` on `u`.`id` = `uyc`.`user_id`
left outer join `profile`.`user_yoga_expertise` `uye` on `u`.`id` = `uye`.`user_id`
group by `u`.`id`;


create view `search`.`review_info` AS
select
   `r`.`id` AS `id`,
   `r`.`created_at` AS `created_at`,
   `r`.`created_by` AS `created_by`,
   `r`.`updated_at` AS `updated_at`,
   `r`.`modified_by` AS `modified_by`,
   `r`.`is_published` AS `is_published`,
   `r`.`review_content` AS `content`,
   `r`.`review_text` AS `text`,
   `r`.`reviewed_by` AS `reviewer_id`,
   `r`.`user_id` AS `user_id`,
   `u`.`email` AS `reviewer_email`,
   `u`.`name` AS `reviewer_name`,
   `u`.`profile_pic` AS `reviewer_profile_pic`,
   GROUP_CONCAT(distinct `ri`.`url`) AS `image_urls`,
   GROUP_CONCAT(distinct `rv`.`url`) AS `video_urls`
FROM `review`.`reviews` `r` left join `user`.`users` `u` on `u`.`id` = `r`.`reviewed_by`
left join `review`.`review_images` `ri` on `r`.`id` = `ri`.`review_id`
left join `review`.`review_videos` `rv` on `r`.`id` = `rv`.`review_id`
group by `r`.`id`;


CREATE VIEW `search`.`video_info` AS
 SELECT
 `i`.`id` AS `id`,
  `i`.`created_at` AS `created_at`,
  `i`.`created_by` AS `created_by`,
  `i`.`modified_by` AS `modified_by`,
  `i`.`updated_at` AS `updated_at`,
  `i`.`description` AS `description`,
  `i`.`title` AS `title`,
  `i`.`url`  AS `url`,
  `i`.`duration`  AS `duration`,
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
    AVG(`r`.`rating`) as `rating`,
    COUNT(distinct `r`.`id`) as `review_count`
FROM
`profile`.`videos` `i`
left outer join `user`.`users` `u` on `u`.`id` = `i`.`user_id`
left outer join `profile`.`user_basic_info` `pbi` on `i`.`user_id` = `pbi`.`user_id`
left outer join `profile`.`interests` `pui` on `i`.`user_id` = `pui`.`user_id`
left outer join `review`.`reviews` `r` on `i`.`user_id` = `r`.`user_id`
group by `i`.`id`
;

CREATE VIEW `search`.`image_info` AS
 SELECT
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
`profile`.`images` `i`
left outer join `user`.`users` `u` on `u`.`id` = `i`.`user_id`
left outer join `profile`.`user_basic_info` `pbi` on `i`.`user_id` = `pbi`.`user_id`
left outer join `profile`.`interests` `pui` on `i`.`user_id` = `pui`.`user_id`
left outer join `review`.`reviews` `r` on `i`.`user_id` = `r`.`user_id`
group by `i`.`id`
;