package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.UserInfo;
import com.justyoga.search.service.interfaces.UserInfoService;
import com.justyoga.search.web.service.interfaces.UserInfoUiService;
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
public class UserInfoUiServiceImpl implements UserInfoUiService {

    private final UserInfoService userInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public UserInfoUiServiceImpl(
            UserInfoService userInfoService, BucketVsUrl bucketVsUrl, MediaClient mediaClient) {
        this.userInfoService = userInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    @Override
    public PageDTO<UserInfo> find(
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

        Page<UserInfo> userInfos;
        if (subLocalityLevel2Id != null) {
            userInfos =
                    userInfoService.findAllByTrainerAndSubLocalityLevel2Id(
                            page, count, sort, order, subLocalityLevel2Id, trainer);
        } else if (subLocalityLevel1Id != null) {
            userInfos =
                    userInfoService.findAllByTrainerAndSubLocalityLevel1Id(
                            page, count, sort, order, subLocalityLevel1Id, trainer);
        } else if (localityId != null) {
            userInfos =
                    userInfoService.findAllByTrainerAndLocalityId(
                            page, count, sort, order, localityId, trainer);
        } else if (administrativeAreaLevel1Id != null) {
            userInfos =
                    userInfoService.findAllByTrainerAndAdministrativeAreaLevel1Id(
                            page, count, sort, order, administrativeAreaLevel1Id, trainer);
        } else if (countryId != null) {
            userInfos =
                    userInfoService.findAllByTrainerAndCountryId(
                            page, count, sort, order, countryId, trainer);
        } else {

            userInfos = userInfoService.findAllByTrainer(page, count, sort, order, trainer);
        }

        userInfos
                .getContent()
                .forEach(
                        userInfo -> {
                            userInfo.setProfilePic(getUrl(userInfo.getProfilePic()));
                            userInfo.setCoverPic(getUrl(userInfo.getCoverPic()));
                        });
        return PageUtil.convert(userInfos);
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
