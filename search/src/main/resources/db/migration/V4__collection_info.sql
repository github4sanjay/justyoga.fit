CREATE OR REPLACE VIEW `search`.`collection_info` AS
 SELECT
  `c`.`id` as `id`,
  `c`.`created_at` as `created_at`,
  `c`.`created_by` as `created_by`,
  `c`.`modified_by` as `modified_by`,
  `c`.`updated_at` as `updated_at`,
  `c`.`administrative_area_level1_id` as `administrative_area_level1_id`,
  `c`.`country_id` as `country_id`,
  `c`.`formatted_address` as `formatted_address`,
  `c`.`geohash_1` as `geohash_1`,
  `c`.`geohash_150` as `geohash_150`,
  `c`.`geohash_5` as `geohash_5`,
  `c`.`geohash_50` as `geohash_50`,
  `c`.`latitude` as `latitude`,
  `c`.`locality_id` as `locality_id`,
  `c`.`longitude` as `longitude`,
  `c`.`postal_code` as `postal_code`,
  `c`.`sub_locality_level1_id` as `sub_locality_level1_id`,
  `c`.`sub_locality_level2_id` as `sub_locality_level2_id`,
  `c`.`url` as `url`,
  `c`.`name` as `name`,
  `c`.`description` as `description`,
    COUNT(distinct `cb`.`id`) as `blog_count`,
    COUNT(distinct `ci`.`id`) as `image_count`,
    COUNT(distinct `cv`.`id`) as `video_count`
FROM
`collection`.`collections` `c`
left outer join `collection`.`collection_blogs` `cb` on `c`.`id` = `cb`.`collection_id`
left outer join `collection`.`collection_images` `ci` on `c`.`id` = `ci`.`collection_id`
left outer join `collection`.`collection_videos` `cv` on `c`.`id` = `cv`.`collection_id`
group by `c`.`id`
;