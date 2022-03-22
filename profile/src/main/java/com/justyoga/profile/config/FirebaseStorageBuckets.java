package com.justyoga.profile.config;

public interface FirebaseStorageBuckets {

    String BUCKET_NAME_FORMAT = "%s/%s_%s"; // bucket_name/imageName_timestamp
    String USER = "trainer";
    String USER_PHOTOS = USER + "/%s/photos";
    String USER_VIDEOS = USER + "/%s/videos";
    String USER_VIDEOS_COVER = USER + "/%s/videos/cover";
}
