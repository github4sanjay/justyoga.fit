package com.justyoga.collection.config;

public interface FirebaseStorageBuckets {

    String BUCKET_NAME_FORMAT = "%s/%s_%s"; // bucket_name/imageName_timestamp
    String COLLECTION = "collections";
    String COLLECTION_COVER = COLLECTION + "/covers";
}
