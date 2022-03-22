package com.justyoga.library.config;

import com.google.firebase.cloud.StorageClient;

/** Returns base bucket of firebase storage gs://credo-f7d83.appspot.com */
public class FirebaseBucketSingleton {

    private static StorageClient instance;

    private FirebaseBucketSingleton() {}

    public static StorageClient getInstance() {
        if (instance == null) {
            synchronized (FirebaseBucketSingleton.class) {
                if (instance == null) {
                    instance = StorageClient.getInstance();
                }
            }
        }
        return instance;
    }
}
