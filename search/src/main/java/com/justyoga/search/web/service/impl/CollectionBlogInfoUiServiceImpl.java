package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.CollectionBlogInfo;
import com.justyoga.search.service.interfaces.CollectionBlogInfoService;
import com.justyoga.search.web.service.interfaces.CollectionBlogInfoUiService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import com.justyoga.util.string.StringUtil;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CollectionBlogInfoUiServiceImpl implements CollectionBlogInfoUiService {

    private final CollectionBlogInfoService collectionBlogInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public CollectionBlogInfoUiServiceImpl(
            CollectionBlogInfoService collectionBlogInfoService,
            BucketVsUrl bucketVsUrl,
            MediaClient mediaClient) {
        this.collectionBlogInfoService = collectionBlogInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    private void covert(CollectionBlogInfo collectionBlogInfo) {
        collectionBlogInfo.setBlogContent(
                StringUtil.getStringFromBase64(collectionBlogInfo.getBlogContent()));
        collectionBlogInfo.setBlogText(
                StringUtil.getStringFromBase64(collectionBlogInfo.getBlogText()));
        collectionBlogInfo.setCoverUrl((getUrl(collectionBlogInfo.getCoverUrl())));
        collectionBlogInfo.setUserCoverPic(getUrl(collectionBlogInfo.getUserCoverPic()));
        collectionBlogInfo.setProfilePic(getUrl(collectionBlogInfo.getProfilePic()));
    }

    @Override
    public PageDTO<CollectionBlogInfo> find(
            Integer page, Integer count, String sort, String order, UUID collectionId) {
        if (page == null) page = 0;
        if (count == null) count = 10;

        Page<CollectionBlogInfo> collectionBlogInfos;
        if (collectionId != null) {
            collectionBlogInfos =
                    collectionBlogInfoService.findAllByCollectionId(
                            page, count, sort, collectionId);
        } else {
            collectionBlogInfos = collectionBlogInfoService.find(page, count, sort, order);
        }

        collectionBlogInfos.getContent().forEach(this::covert);
        return PageUtil.convert(collectionBlogInfos);
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
