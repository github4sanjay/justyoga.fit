package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.ImageInfo;
import com.justyoga.search.service.interfaces.ImageInfoService;
import com.justyoga.search.web.service.interfaces.ImageInfoUiService;
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
public class ImageInfoUiServiceImpl implements ImageInfoUiService {

    private final ImageInfoService imageInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public ImageInfoUiServiceImpl(
            ImageInfoService imageInfoService, BucketVsUrl bucketVsUrl, MediaClient mediaClient) {
        this.imageInfoService = imageInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    @Override
    public PageDTO<ImageInfo> find(
            Integer page,
            Integer count,
            String sort,
            String order,
            Boolean trainer,
            UUID countryId,
            UUID administrativeAreaLevel1Id,
            UUID localityId,
            UUID subLocalityLevel1Id,
            UUID subLocalityLevel2Id,
            UUID userId) {
        if (page == null) page = 0;
        if (count == null) count = 10;

        Page<ImageInfo> imageInfos;
        if (userId != null) {
            imageInfos = imageInfoService.findAllByUserId(page, count, sort, order, userId);
        } else if (subLocalityLevel2Id != null && trainer != null) {
            imageInfos =
                    imageInfoService.findAllByTrainerAndSubLocalityLevel2Id(
                            page, count, sort, subLocalityLevel2Id, trainer);
        } else if (subLocalityLevel2Id != null) {
            imageInfos =
                    imageInfoService.findAllBySubLocalityLevel2Id(
                            page, count, sort, order, subLocalityLevel2Id);
        } else if (subLocalityLevel1Id != null && trainer != null) {
            imageInfos =
                    imageInfoService.findAllByTrainerAndSubLocalityLevel1Id(
                            page, count, sort, subLocalityLevel1Id, trainer);
        } else if (subLocalityLevel1Id != null) {
            imageInfos =
                    imageInfoService.findAllBySubLocalityLevel1Id(
                            page, count, sort, order, subLocalityLevel1Id);
        } else if (localityId != null && trainer != null) {
            imageInfos =
                    imageInfoService.findAllByTrainerAndLocalityId(
                            page, count, sort, localityId, trainer);
        } else if (localityId != null) {
            imageInfos = imageInfoService.findAllByLocalityId(page, count, sort, order, localityId);
        } else if (administrativeAreaLevel1Id != null && trainer != null) {
            imageInfos =
                    imageInfoService.findAllByTrainerAndAdministrativeAreaLevel1Id(
                            page, count, sort, administrativeAreaLevel1Id, trainer);
        } else if (administrativeAreaLevel1Id != null) {
            imageInfos =
                    imageInfoService.findAllByAdministrativeAreaLevel1Id(
                            page, count, sort, order, administrativeAreaLevel1Id);
        } else if (countryId != null && trainer != null) {
            imageInfos =
                    imageInfoService.findAllByTrainerAndCountryId(
                            page, count, sort, countryId, trainer);
        } else if (countryId != null) {
            imageInfos = imageInfoService.findAllByCountryId(page, count, sort, order, countryId);
        } else if (trainer != null) {
            imageInfos = imageInfoService.findAllByTrainer(page, count, sort, trainer);
        } else {
            imageInfos = imageInfoService.find(page, count, sort, order);
        }

        imageInfos
                .getContent()
                .forEach(
                        imageInfo -> {
                            imageInfo.setUrl(getUrl(imageInfo.getUrl()));
                            imageInfo.setUserCoverPic(getUrl(imageInfo.getUserCoverPic()));
                            imageInfo.setProfilePic(getUrl(imageInfo.getProfilePic()));
                        });
        return PageUtil.convert(imageInfos);
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
