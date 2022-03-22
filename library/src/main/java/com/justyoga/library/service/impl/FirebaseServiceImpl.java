package com.justyoga.library.service.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageException;
import com.justyoga.library.config.FirebaseBucketSingleton;
import com.justyoga.library.service.interfaces.FirebaseService;
import com.justyoga.util.dto.profile.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private static final Long DAYS_FOR_IMAGE_EXPIRATION = 1L;

    @Override
    public String upload(
            MultipartFile imageFile,
            String bucketName,
            MediaType mediaType,
            Long expiration,
            TimeUnit timeUnit)
            throws IOException {
        Bucket bucket = FirebaseBucketSingleton.getInstance().bucket();
        InputStream inputStream = imageFile.getInputStream();
        switch (mediaType) {
            case IMAGE:
                return uploadImage(inputStream, bucketName, bucket, expiration, timeUnit);
            case VIDEO:
                return uploadVideo(inputStream, bucketName, bucket, expiration, timeUnit);
        }
        return null;
    }

    @Override
    public String getSignedUrl(String bucketName, Long expiration, TimeUnit timeUnit) {
        Bucket bucket = FirebaseBucketSingleton.getInstance().bucket();
        Blob blob = bucket.get(bucketName);
        URL url = blob.signUrl(DAYS_FOR_IMAGE_EXPIRATION, TimeUnit.DAYS);
        return url.toString();
    }

    public String uploadImage(
            InputStream inputStream,
            String bucketName,
            Bucket bucket,
            Long expiration,
            TimeUnit timeUnit) {
        Blob blob = bucket.create(bucketName, inputStream, "image/*");
        URL url = blob.signUrl(DAYS_FOR_IMAGE_EXPIRATION, TimeUnit.DAYS);
        return url.toString();
    }

    public String uploadVideo(
            InputStream inputStream,
            String bucketName,
            Bucket bucket,
            Long expiration,
            TimeUnit timeUnit) {
        Blob blob = bucket.create(bucketName, inputStream, "video/*");
        URL url = blob.signUrl(DAYS_FOR_IMAGE_EXPIRATION, TimeUnit.DAYS);
        return url.toString();
    }

    @Override
    public boolean delete(String bucketName) throws StorageException {
        Bucket bucket = FirebaseBucketSingleton.getInstance().bucket();
        Blob blob = bucket.get(bucketName);
        return blob.delete();
    }
}
