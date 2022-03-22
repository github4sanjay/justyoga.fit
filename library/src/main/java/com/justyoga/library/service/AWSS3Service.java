package com.justyoga.library.service;

import com.amazonaws.AmazonServiceException;
import com.justyoga.library.service.impl.AWSS3ServiceImpl;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

    String uploadFile(
            MultipartFile multipartFile, String fileName, Long expiration, TimeUnit timeUnit);

    String uploadPublicFile(MultipartFile multipartFile, String fileName)
            throws AmazonServiceException;

    String getSignedUrl(String fileName, Long expiration, TimeUnit timeUnit);

    void delete(String bucket);

    AWSS3ServiceImpl.MediaURL generateUploadUrl(UUID userId);

    AWSS3ServiceImpl.MediaURL generateUploadUrlPublic(UUID userId);
}
