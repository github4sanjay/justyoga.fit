package com.justyoga.search.web.service.impl;

import com.justyoga.search.web.service.interfaces.BlogInfoUiService;
import com.justyoga.search.web.service.interfaces.FeedUiService;
import com.justyoga.search.web.service.interfaces.ImageInfoUiService;
import com.justyoga.search.web.service.interfaces.VideoInfoUiService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedUiServiceImpl implements FeedUiService {

    private final VideoInfoUiService videoInfoUiService;
    private final ImageInfoUiService imageInfoUiService;
    private final BlogInfoUiService blogInfoUiService;

    @Autowired
    public FeedUiServiceImpl(
            VideoInfoUiService videoInfoUiService,
            ImageInfoUiService imageInfoUiService,
            BlogInfoUiService blogInfoUiService) {
        this.videoInfoUiService = videoInfoUiService;
        this.imageInfoUiService = imageInfoUiService;
        this.blogInfoUiService = blogInfoUiService;
    }

    @Override
    public Feed find(
            Integer page,
            Integer count,
            String sort,
            String order,
            UUID userId,
            UUID countryId,
            UUID administrativeAreaLevel1Id,
            UUID localityId,
            UUID subLocalityLevel1Id,
            UUID subLocalityLevel2Id) {

        var blogInfoPageDTO =
                blogInfoUiService.find(
                        page,
                        count,
                        sort,
                        order,
                        false,
                        countryId,
                        administrativeAreaLevel1Id,
                        localityId,
                        subLocalityLevel1Id,
                        subLocalityLevel2Id,
                        userId);

        var imageInfoPageDTO =
                imageInfoUiService.find(
                        page,
                        count,
                        sort,
                        order,
                        false,
                        countryId,
                        administrativeAreaLevel1Id,
                        localityId,
                        subLocalityLevel1Id,
                        subLocalityLevel2Id,
                        userId);

        var videoInfoPageDTO =
                videoInfoUiService.find(
                        page,
                        count,
                        sort,
                        order,
                        false,
                        countryId,
                        administrativeAreaLevel1Id,
                        localityId,
                        subLocalityLevel1Id,
                        subLocalityLevel2Id,
                        userId);

        return Feed.builder()
                .blogs(blogInfoPageDTO)
                .images(imageInfoPageDTO)
                .videos(videoInfoPageDTO)
                .build();
    }
}
