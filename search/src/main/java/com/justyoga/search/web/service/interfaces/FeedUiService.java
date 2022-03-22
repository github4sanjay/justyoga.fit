package com.justyoga.search.web.service.interfaces;

import com.justyoga.search.domain.model.BlogInfo;
import com.justyoga.search.domain.model.ImageInfo;
import com.justyoga.search.domain.model.VideoInfo;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

public interface FeedUiService {
    Feed find(
            Integer page,
            Integer count,
            String sort,
            String order,
            UUID userId,
            UUID countryId,
            UUID administrativeAreaLevel1Id,
            UUID localityId,
            UUID subLocalityLevel1Id,
            UUID subLocalityLevel2Id);

    @Builder
    @Data
    class Feed {
        private PageDTO<ImageInfo> images;
        private PageDTO<VideoInfo> videos;
        private PageDTO<BlogInfo> blogs;
    }
}
