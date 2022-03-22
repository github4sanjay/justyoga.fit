package com.justyoga.blog.config;

public interface FirebaseStorageBuckets {

    String BUCKET_NAME_FORMAT = "%s/%s_%s"; // bucket_name/imageName_timestamp
    String BLOG = "blogs";
    String BLOG_PHOTOS = BLOG + "/%s/photos";
    String BLOG_VIDEOS = BLOG + "/%s/videos";
}
