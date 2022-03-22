package com.justyoga.search.web.service.interfaces;

import com.justyoga.search.domain.model.CollectionVideoInfo;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface CollectionVideoInfoUiService {
    PageDTO<CollectionVideoInfo> find(
            Integer page, Integer count, String sort, String order, UUID collectionId);
}
