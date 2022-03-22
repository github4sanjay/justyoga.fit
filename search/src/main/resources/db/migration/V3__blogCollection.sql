CREATE OR REPLACE VIEW `search`.`blog_info` AS
 SELECT
 `b`.`id` AS `id`,
  `b`.`created_at` AS `created_at`,
  `b`.`created_by` AS `created_by`,
  `b`.`modified_by` AS `modified_by`,
  `b`.`updated_at` AS `updated_at`,
  `b`.`blog_title` AS `title`,
  `b`.`blog_content` AS `blog_content`,
  `b`.`blog_text` AS `blog_text`,
  `b`.`cover_url` As `cover_url`,
  `b`.`is_published`  AS `is_published`,
  `b`.`user_id` AS `user_id`,
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
    COUNT(distinct `r`.`id`) as `review_count`,
    COUNT(distinct `bi`.`id`) as `image_count`,
    COUNT(distinct `bv`.`id`) as `video_count`
FROM
`blog`.`blogs` `b`
left outer join `user`.`users` `u` on `u`.`id` = `b`.`user_id`
left outer join `profile`.`user_basic_info` `pbi` on `b`.`user_id` = `pbi`.`user_id`
left outer join `profile`.`interests` `pui` on `b`.`user_id` = `pui`.`user_id`
left outer join `review`.`reviews` `r` on `b`.`user_id` = `r`.`user_id`
left outer join `blog`.`blog_images` `bi` on `b`.`id` = `bi`.`blog_id`
left outer join `blog`.`blog_videos` `bv` on `b`.`id` = `bv`.`blog_id`
group by `b`.`id`
;


CREATE OR REPLACE VIEW `search`.`collection_blog_info` AS
 SELECT
 `cb`.`collection_id` AS `collection_id`,   
  `b`.`id` AS `id`,
  `b`.`created_at` AS `created_at`,
  `b`.`created_by` AS `created_by`,
  `b`.`modified_by` AS `modified_by`,
  `b`.`updated_at` AS `updated_at`,
  `b`.`blog_content` AS `blog_content`,
  `b`.`blog_text` AS `blog_text`,
  `b`.`cover_url` As `cover_url`,
  `b`.`is_published`  AS `is_published`,
  `b`.`user_id` AS `user_id`,
  `b`.`blog_title` AS `title`,
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
    COUNT(distinct `r`.`id`) as `user_review_count`,
    COUNT(distinct `bi`.`id`) as `image_count`,
    COUNT(distinct `bv`.`id`) as `video_count`
FROM
`collection`.`collection_blogs` `cb`
left outer join `blog`.blogs `b` on `cb`.`blog_id` = `b`.id
left outer join `user`.`users` `u` on `u`.`id` = `b`.`user_id`
left outer join `profile`.`user_basic_info` `pbi` on `b`.`user_id` = `pbi`.`user_id`
left outer join `profile`.`interests` `pui` on `b`.`user_id` = `pui`.`user_id`
left outer join `review`.`reviews` `r` on `b`.`user_id` = `r`.`user_id`
left outer join `blog`.`blog_images` `bi` on `b`.`id` = `bi`.`blog_id`
left outer join `blog`.`blog_videos` `bv` on `b`.`id` = `bv`.`blog_id`
group by `cb`.`id`
;