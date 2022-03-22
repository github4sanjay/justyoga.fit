package com.justyoga.library.web.api;

import com.amazonaws.AmazonServiceException;
import com.google.cloud.storage.StorageException;
import com.google.gson.Gson;
import com.justyoga.library.service.AWSS3Service;
import com.justyoga.library.service.impl.AWSS3ServiceImpl;
import com.justyoga.util.dto.library.MediaDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Authentication APIs")
@Slf4j
public class MediaController {

    private final AWSS3Service awss3Service;

    @Autowired
    public MediaController(AWSS3Service awss3Service) {
        this.awss3Service = awss3Service;
    }

    @PostMapping(value = "/media-collection")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<String>>> mediaCollection(
            @RequestPart MultipartFile[] images, @RequestPart String request) {
        Gson gson = new Gson();
        MediaDTO mediaDTO = gson.fromJson(request, MediaDTO.class);
        List<String> urls = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : images) {
                String url =
                        awss3Service.uploadFile(
                                multipartFile,
                                mediaDTO.getBucketName(),
                                mediaDTO.getExpiration(),
                                mediaDTO.getTimeUnit());
                urls.add(url);
            }
        } catch (AmazonServiceException e) {
            throw new AppException(e.getMessage(), AppStatusCode.MEDIA_ERROR);
        }
        return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, urls), HttpStatus.OK);
    }

    @PostMapping(value = "/media")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> media(
            @RequestPart MultipartFile image, @RequestPart String request) {
        log.info("request {}", request);
        Gson gson = new Gson();
        MediaDTO mediaDTO = gson.fromJson(request, MediaDTO.class);
        try {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            Status.SUCCESS,
                            awss3Service.uploadFile(
                                    image,
                                    mediaDTO.getBucketName(),
                                    mediaDTO.getExpiration(),
                                    mediaDTO.getTimeUnit())),
                    HttpStatus.OK);
        } catch (AmazonServiceException e) {
            throw new AppException(e.getMessage(), AppStatusCode.MEDIA_ERROR);
        }
    }

    @GetMapping("/media")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> getUrl(
            @RequestParam String bucket,
            @RequestParam Long expiration,
            @RequestParam TimeUnit timeUnit) {
        try {
            String signedUrl = awss3Service.getSignedUrl(bucket, expiration, timeUnit);
            return new ResponseEntity<>(
                    new BaseResponse<>(Status.SUCCESS, signedUrl), HttpStatus.OK);
        } catch (StorageException e) {
            throw new AppException(e.getMessage(), AppStatusCode.MEDIA_ERROR);
        }
    }

    @DeleteMapping("/media")
    @ResponseBody
    public ResponseEntity<BaseResponse<String>> delete(@RequestParam String bucket) {
        try {
            awss3Service.delete(bucket);
            return new ResponseEntity<>(new BaseResponse<>(Status.SUCCESS, null), HttpStatus.OK);
        } catch (AmazonServiceException e) {
            throw new AppException(e.getMessage(), AppStatusCode.MEDIA_ERROR);
        }
    }

    @PostMapping(value = "/media-upload")
    @ResponseBody
    public ResponseEntity<BaseResponse<MediaUploadResponse>> mediaPublic(
            @RequestPart MultipartFile media) {
        try {
            String id = UUID.randomUUID().toString();
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            Status.SUCCESS,
                            MediaUploadResponse.builder()
                                    .id(id)
                                    .downloadUrl(awss3Service.uploadPublicFile(media, id))
                                    .build()),
                    HttpStatus.OK);
        } catch (AmazonServiceException e) {
            throw new AppException(e.getMessage(), AppStatusCode.MEDIA_ERROR);
        }
    }

    @PostMapping(value = "/media-upload/url/public")
    @ResponseBody
    public ResponseEntity<BaseResponse<MediaUploadResponse>> mediaUploadUrlPublic(
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        try {
            AWSS3ServiceImpl.MediaURL mediaURL = awss3Service.generateUploadUrlPublic(userId);
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            Status.SUCCESS,
                            MediaUploadResponse.builder()
                                    .id(mediaURL.getId())
                                    .uploadUrl(mediaURL.getUploadUrl())
                                    .downloadUrl(mediaURL.getDownloadUrl())
                                    .build()),
                    HttpStatus.OK);
        } catch (AmazonServiceException e) {
            throw new AppException(e.getMessage(), AppStatusCode.MEDIA_ERROR);
        }
    }

    @PostMapping(value = "/media-upload/url")
    @ResponseBody
    public ResponseEntity<BaseResponse<MediaUploadResponse>> mediaPublicUrl(
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        try {
            AWSS3ServiceImpl.MediaURL mediaURL = awss3Service.generateUploadUrl(userId);
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            Status.SUCCESS,
                            MediaUploadResponse.builder()
                                    .id(mediaURL.getId())
                                    .uploadUrl(mediaURL.getUploadUrl())
                                    .downloadUrl(mediaURL.getDownloadUrl())
                                    .build()),
                    HttpStatus.OK);
        } catch (AmazonServiceException e) {
            throw new AppException(e.getMessage(), AppStatusCode.MEDIA_ERROR);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class MediaUploadResponse {
        private String id;
        private String uploadUrl;
        private String downloadUrl;
    }
}
