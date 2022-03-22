package com.justyoga.search.web.service.interfaces;

import com.justyoga.search.domain.model.CollectionBlogInfo;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface CollectionBlogInfoUiService {
    PageDTO<CollectionBlogInfo> find(
            Integer page, Integer count, String sort, String order, UUID collectionId);
}
