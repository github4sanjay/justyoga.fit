package com.justyoga.library.service.interfaces;

import com.google.cloud.storage.StorageException;
import com.justyoga.util.dto.profile.MediaType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.springframework.web.multipart.MultipartFile;

public interface FirebaseService {

    String upload(
            MultipartFile imageFile,
            String bucketName,
            MediaType mediaType,
            Long expiration,
            TimeUnit timeUnit)
            throws IOException;

    String getSignedUrl(String bucketName, Long expiration, TimeUnit timeUnit);

    boolean delete(String bucketName) throws StorageException;
}
