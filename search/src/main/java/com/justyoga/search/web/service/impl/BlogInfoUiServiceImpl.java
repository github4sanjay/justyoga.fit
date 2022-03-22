package com.justyoga.search.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.search.cache.BucketVsUrl;
import com.justyoga.search.domain.model.BlogInfo;
import com.justyoga.search.service.interfaces.BlogInfoService;
import com.justyoga.search.web.service.interfaces.BlogInfoUiService;
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
public class BlogInfoUiServiceImpl implements BlogInfoUiService {

    private final BlogInfoService blogInfoService;
    private final BucketVsUrl bucketVsUrl;
    private final MediaClient mediaClient;

    @Autowired
    public BlogInfoUiServiceImpl(
            BlogInfoService blogInfoService, BucketVsUrl bucketVsUrl, MediaClient mediaClient) {
        this.blogInfoService = blogInfoService;
        this.bucketVsUrl = bucketVsUrl;
        this.mediaClient = mediaClient;
    }

    private void convert(BlogInfo blogInfo) {
        blogInfo.setBlogContent(StringUtil.getStringFromBase64(blogInfo.getBlogContent()));
        blogInfo.setBlogText(StringUtil.getStringFromBase64(blogInfo.getBlogText()));
        blogInfo.setCoverUrl((getUrl(blogInfo.getCoverUrl())));
        blogInfo.setUserCoverPic(getUrl(blogInfo.getUserCoverPic()));
        blogInfo.setProfilePic(getUrl(blogInfo.getProfilePic()));
    }

    @Override
    public PageDTO<BlogInfo> find(
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

        Page<BlogInfo> blogInfoPage;
        if (userId != null) {
            blogInfoPage = blogInfoService.findAllByUserId(page, count, sort, order, userId);
        } else if (subLocalityLevel2Id != null && trainer != null) {
            blogInfoPage =
                    blogInfoService.findAllByTrainerAndSubLocalityLevel2Id(
                            page, count, sort, subLocalityLevel2Id, trainer);
        } else if (subLocalityLevel2Id != null) {
            blogInfoPage =
                    blogInfoService.findAllBySubLocalityLevel2Id(
                            page, count, sort, order, subLocalityLevel2Id);
        } else if (subLocalityLevel1Id != null && trainer != null) {
            blogInfoPage =
                    blogInfoService.findAllByTrainerAndSubLocalityLevel1Id(
                            page, count, sort, subLocalityLevel1Id, trainer);
        } else if (subLocalityLevel1Id != null) {
            blogInfoPage =
                    blogInfoService.findAllBySubLocalityLevel1Id(
                            page, count, sort, order, subLocalityLevel1Id);
        } else if (localityId != null && trainer != null) {
            blogInfoPage =
                    blogInfoService.findAllByTrainerAndLocalityId(
                            page, count, sort, localityId, trainer);
        } else if (localityId != null) {
            blogInfoPage =
                    blogInfoService.findAllByLocalityId(page, count, sort, order, localityId);
        } else if (administrativeAreaLevel1Id != null && trainer != null) {
            blogInfoPage =
                    blogInfoService.findAllByTrainerAndAdministrativeAreaLevel1Id(
                            page, count, sort, administrativeAreaLevel1Id, trainer);
        } else if (administrativeAreaLevel1Id != null) {
            blogInfoPage =
                    blogInfoService.findAllByAdministrativeAreaLevel1Id(
                            page, count, sort, order, administrativeAreaLevel1Id);
        } else if (countryId != null && trainer != null) {
            blogInfoPage =
                    blogInfoService.findAllByTrainerAndCountryId(
                            page, count, sort, countryId, trainer);
        } else if (countryId != null) {
            blogInfoPage = blogInfoService.findAllByCountryId(page, count, sort, order, countryId);
        } else if (trainer != null) {
            blogInfoPage = blogInfoService.findAllByTrainer(page, count, sort, trainer);
        } else {
            blogInfoPage = blogInfoService.find(page, count, sort, order);
        }
        blogInfoPage.getContent().forEach(this::convert);
        return PageUtil.convert(blogInfoPage);
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
