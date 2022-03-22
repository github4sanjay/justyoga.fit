package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.CollectionImageInfo;
import com.justyoga.search.service.interfaces.CollectionImageInfoService;
import com.justyoga.search.web.service.interfaces.CollectionImageInfoUiService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CollectionImageInfoUiServiceImpl implements CollectionImageInfoUiService {

    private final CollectionImageInfoService collectionImageInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public CollectionImageInfoUiServiceImpl(
            CollectionImageInfoService collectionImageInfoService,
            BucketVsUrl bucketVsUrl,
            MediaClient mediaClient) {
        this.collectionImageInfoService = collectionImageInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    @Override
    public PageDTO<CollectionImageInfo> find(
            Integer page, Integer count, String sort, String order, UUID collectionId) {
        if (page == null) page = 0;
        if (count == null) count = 10;

        Page<CollectionImageInfo> collectionImageInfos;
        if (collectionId != null) {
            collectionImageInfos =
                    collectionImageInfoService.findAllByCollectionId(
                            page, count, sort, collectionId);
        } else {
            collectionImageInfos = collectionImageInfoService.find(page, count, sort, order);
        }

        collectionImageInfos.getContent().forEach(this::convert);
        return PageUtil.convert(collectionImageInfos);
    }

    private void convert(CollectionImageInfo imageInfo) {
        imageInfo.setUrl(getUrl(imageInfo.getUrl()));
        imageInfo.setUserCoverPic(getUrl(imageInfo.getUserCoverPic()));
        imageInfo.setProfilePic(getUrl(imageInfo.getProfilePic()));
    }

    private String getUrl(String bucketName) {
        if (bucketName == null) return null;
        String url = bucketVsUrl.get(bucketName);
        if (url == null) {
            ResponseEntity<BaseResponse<String>> coverUrlResponse =
                    mediaClient.getUrl(bucketName, 1L, TimeUnit.DAYS);
            if (coverUrlResponse.getStatusCode() == HttpStatus.OK
                    && coverUrlResponse.getBody() != null
                    && coverUrlResponse.getBody().getStatus() == Status.SUCCESS) {
                url = coverUrlResponse.getBody().getData();
                bucketVsUrl.put(bucketName, url);
            } else {
                throw new AppException("Media client fails", AppStatusCode.MEDIA_ERROR);
            }
        }
        return url;
    }
}
