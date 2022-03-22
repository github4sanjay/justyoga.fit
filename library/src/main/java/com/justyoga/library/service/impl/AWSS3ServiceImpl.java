package com.justyoga.library.service.impl;

import static com.amazonaws.services.s3.model.CannedAccessControlList.Private;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.justyoga.library.service.AWSS3Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    private final AmazonS3 amazonS3;
    private final String bucketName;
    private final String publicBucketName;
    private static final String PUBLIC_MEDIA_BUCKET = "public";
    String AWS_S3_PUBLIC_URL_FORMAT = "https://%s.s3.ap-south-1.amazonaws.com/%s/%s"; // "%s/%s_%s";

    @Autowired
    public AWSS3ServiceImpl(
            AmazonS3 amazonS3,
            @Value("${aws.s3.bucket}") String bucketName,
            @Value("${aws.s3.public.bucket}") String publicBucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
        this.publicBucketName = publicBucketName;
    }

    @Override
    public String uploadFile(
            final MultipartFile multipartFile,
            final String fileName,
            Long expiration,
            TimeUnit timeUnit)
            throws AmazonServiceException {
        final File file = convertMultiPartFileToFile(multipartFile);
        var url = uploadFileToS3Bucket(bucketName, file, fileName, expiration, timeUnit);
        file.delete(); // To remove the file locally created in the project folder.
        return url;
    }

    @Override
    public String uploadPublicFile(final MultipartFile multipartFile, final String fileName)
            throws AmazonServiceException {
        final File file = convertMultiPartFileToFile(multipartFile);
        var url = uploadFileToS3BucketPublic(publicBucketName, file, fileName);
        file.delete(); // To remove the file locally created in the project folder.
        return url;
    }

    @Override
    public String getSignedUrl(String fileName, Long expiration, TimeUnit timeUnit) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(getExpiration(expiration, timeUnit));
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    @Override
    public void delete(String fileName) throws AmazonServiceException {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    @Override
    public MediaURL generateUploadUrl(UUID userId) {
        String fileName = UUID.randomUUID().toString();
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName + "/" + userId, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getExpiration(10L, TimeUnit.MINUTES));
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return MediaURL.builder()
                .id(userId + "/" + fileName)
                .uploadUrl(url.toString())
                .downloadUrl(null)
                .build();
    }

    @Override
    public MediaURL generateUploadUrlPublic(UUID userId) {
        String fileName = UUID.randomUUID().toString();
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(publicBucketName + "/" + userId, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getExpiration(10L, TimeUnit.MINUTES));
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return MediaURL.builder()
                .id(userId + "/" + fileName)
                .uploadUrl(url.toString())
                .downloadUrl(
                        String.format(AWS_S3_PUBLIC_URL_FORMAT, publicBucketName, userId, fileName))
                .build();
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            log.error("Error converting the multi-part file to file= {}", ex.getMessage());
        }
        return file;
    }

    private String uploadFileToS3Bucket(
            final String bucketName,
            final File file,
            final String fileName,
            Long expiration,
            TimeUnit timeUnit) {
        log.info("Uploading file with name= " + fileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        putObjectRequest.setCannedAcl(Private);
        amazonS3.putObject(putObjectRequest);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(getExpiration(expiration, timeUnit));
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private String uploadFileToS3BucketPublic(
            final String publicBucketName, final File file, final String fileName) {
        log.info(
                "Uploading file with name= "
                        + publicBucketName
                        + "/"
                        + PUBLIC_MEDIA_BUCKET
                        + "/"
                        + fileName);
        final PutObjectRequest putObjectRequest =
                new PutObjectRequest(publicBucketName, PUBLIC_MEDIA_BUCKET + "/" + fileName, file);
        putObjectRequest.setCannedAcl(Private);
        amazonS3.putObject(putObjectRequest);
        return String.format(
                AWS_S3_PUBLIC_URL_FORMAT, publicBucketName, PUBLIC_MEDIA_BUCKET, fileName);
    }

    private Date getExpiration(Long expiration, TimeUnit timeUnit) {
        return Date.from(Instant.now().plus(expiration, timeUnit.toChronoUnit()));
    }

    @Builder
    @Getter
    public static class MediaURL {
        private final String id;
        private final String uploadUrl;
        private final String downloadUrl;
    }
}
