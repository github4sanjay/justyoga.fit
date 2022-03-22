package com.justyoga.bookmark.config;

public interface FirebaseStorageBuckets {

    String BUCKET_NAME_FORMAT = "%s/%s_%s"; // bucket_name/imageName_timestamp
    String REVIEW = "reviews";
    String REVIEW_PHOTOS = REVIEW + "/%s/photos";
    String REVIEW_VIDEOS = REVIEW + "/%s/videos";
}
