package com.justyoga.search.web.service.interfaces;

import com.justyoga.search.domain.model.CollectionImageInfo;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface CollectionImageInfoUiService {
    PageDTO<CollectionImageInfo> find(
            Integer page, Integer count, String sort, String order, UUID collectionId);
}
