package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.CollectionVideoInfo;
import com.justyoga.search.service.interfaces.CollectionVideoInfoService;
import com.justyoga.search.web.service.interfaces.CollectionVideoInfoUiService;
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
public class CollectionVideoInfoUiServiceImpl implements CollectionVideoInfoUiService {

    private final CollectionVideoInfoService collectionVideoInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public CollectionVideoInfoUiServiceImpl(
            CollectionVideoInfoService collectionVideoInfoService,
            BucketVsUrl bucketVsUrl,
            MediaClient mediaClient) {
        this.collectionVideoInfoService = collectionVideoInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    @Override
    public PageDTO<CollectionVideoInfo> find(
            Integer page, Integer count, String sort, String order, UUID collectionId) {
        if (page == null) page = 0;
        if (count == null) count = 10;

        Page<CollectionVideoInfo> collectionVideoInfos;
        if (collectionId != null) {
            collectionVideoInfos =
                    collectionVideoInfoService.findAllByCollectionId(
                            page, count, sort, collectionId);
        } else {
            collectionVideoInfos = collectionVideoInfoService.find(page, count, sort, order);
        }

        collectionVideoInfos
                .getContent()
                .forEach(
                        collectionVideoInfo -> {
                            collectionVideoInfo.setUrl(getUrl(collectionVideoInfo.getUrl()));
                            collectionVideoInfo.setCoverPic(
                                    getUrl(collectionVideoInfo.getCoverPic()));
                            collectionVideoInfo.setUserCoverPic(
                                    getUrl(collectionVideoInfo.getUserCoverPic()));
                            collectionVideoInfo.setProfilePic(
                                    getUrl(collectionVideoInfo.getProfilePic()));
                        });
        return PageUtil.convert(collectionVideoInfos);
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
