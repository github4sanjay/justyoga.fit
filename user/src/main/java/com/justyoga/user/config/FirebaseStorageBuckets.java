package com.justyoga.user.config;

public interface FirebaseStorageBuckets {

    String BUCKET_NAME_FORMAT = "%s/%s_%s"; // bucket_name/imageName_timestamp
    String USER = "user";
    String USER_PROFILE_PHOTOS = USER + "/%s/profile_photos";
}
