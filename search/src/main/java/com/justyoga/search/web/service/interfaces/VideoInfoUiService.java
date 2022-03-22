package com.justyoga.search.web.service.interfaces;

import com.justyoga.search.domain.model.VideoInfo;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface VideoInfoUiService {
    PageDTO<VideoInfo> find(
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
            UUID userId);
}
