package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.VideoInfo;
import com.justyoga.search.service.interfaces.VideoInfoService;
import com.justyoga.search.web.service.interfaces.VideoInfoUiService;
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
public class VideoInfoUiServiceImpl implements VideoInfoUiService {

    private final VideoInfoService videoInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public VideoInfoUiServiceImpl(
            VideoInfoService videoInfoService, BucketVsUrl bucketVsUrl, MediaClient mediaClient) {
        this.videoInfoService = videoInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    @Override
    public PageDTO<VideoInfo> find(
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

        Page<VideoInfo> videoInfos;
        if (userId != null) {
            videoInfos = videoInfoService.findAllByUserId(page, count, sort, order, userId);
        } else if (subLocalityLevel2Id != null && trainer != null) {
            videoInfos =
                    videoInfoService.findAllByTrainerAndSubLocalityLevel2Id(
                            page, count, sort, subLocalityLevel2Id, trainer);
        } else if (subLocalityLevel2Id != null) {
            videoInfos =
                    videoInfoService.findAllBySubLocalityLevel2Id(
                            page, count, sort, order, subLocalityLevel2Id);
        } else if (subLocalityLevel1Id != null && trainer != null) {
            videoInfos =
                    videoInfoService.findAllByTrainerAndSubLocalityLevel1Id(
                            page, count, sort, subLocalityLevel1Id, trainer);
        } else if (subLocalityLevel1Id != null) {
            videoInfos =
                    videoInfoService.findAllBySubLocalityLevel1Id(
                            page, count, sort, order, subLocalityLevel1Id);
        } else if (localityId != null && trainer != null) {
            videoInfos =
                    videoInfoService.findAllByTrainerAndLocalityId(
                            page, count, sort, localityId, trainer);
        } else if (localityId != null) {
            videoInfos = videoInfoService.findAllByLocalityId(page, count, sort, order, localityId);
        } else if (administrativeAreaLevel1Id != null && trainer != null) {
            videoInfos =
                    videoInfoService.findAllByTrainerAndAdministrativeAreaLevel1Id(
                            page, count, sort, administrativeAreaLevel1Id, trainer);
        } else if (administrativeAreaLevel1Id != null) {
            videoInfos =
                    videoInfoService.findAllByAdministrativeAreaLevel1Id(
                            page, count, sort, order, administrativeAreaLevel1Id);
        } else if (countryId != null && trainer != null) {
            videoInfos =
                    videoInfoService.findAllByTrainerAndCountryId(
                            page, count, sort, countryId, trainer);
        } else if (countryId != null) {
            videoInfos = videoInfoService.findAllByCountryId(page, count, sort, order, countryId);
        } else if (trainer != null) {
            videoInfos = videoInfoService.findAllByTrainer(page, count, sort, trainer);
        } else {
            videoInfos = videoInfoService.find(page, count, sort, order);
        }

        videoInfos
                .getContent()
                .forEach(
                        videoInfo -> {
                            videoInfo.setUrl(getUrl(videoInfo.getUrl()));
                            videoInfo.setCoverPic(getUrl(videoInfo.getCoverPic()));
                            videoInfo.setUserCoverPic(getUrl(videoInfo.getUserCoverPic()));
                            videoInfo.setProfilePic(getUrl(videoInfo.getProfilePic()));
                        });
        return PageUtil.convert(videoInfos);
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
