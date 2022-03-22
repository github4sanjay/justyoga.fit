package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.CollectionBlogInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface CollectionBlogInfoService {
    Page<CollectionBlogInfo> find(Integer page, Integer count, String sort, String order);

    Page<CollectionBlogInfo> findAllByCollectionId(
            Integer page, Integer count, String sort, UUID collectionId);
}
