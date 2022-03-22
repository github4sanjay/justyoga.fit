package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.CollectionInfo;
import com.justyoga.search.service.interfaces.CollectionInfoService;
import com.justyoga.search.web.service.interfaces.CollectionInfoUiService;
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
public class CollectionInfoUiServiceImpl implements CollectionInfoUiService {

    private final CollectionInfoService collectionInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public CollectionInfoUiServiceImpl(
            CollectionInfoService collectionInfoService,
            BucketVsUrl bucketVsUrl,
            MediaClient mediaClient) {
        this.collectionInfoService = collectionInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    @Override
    public PageDTO<CollectionInfo> find(
            Integer page,
            Integer count,
            String sort,
            String order,
            Boolean trainer,
            UUID countryId,
            UUID administrativeAreaLevel1Id,
            UUID localityId,
            UUID subLocalityLevel1Id,
            UUID subLocalityLevel2Id) {
        if (page == null) page = 0;
        if (count == null) count = 10;

        Page<CollectionInfo> all = collectionInfoService.findAll(page, count, sort, order);

        all.getContent()
                .forEach(
                        collectionInfo -> {
                            collectionInfo.setCoverUrl(getUrl(collectionInfo.getCoverUrl()));
                        });

        return PageUtil.convert(all);
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
