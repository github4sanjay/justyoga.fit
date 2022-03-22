package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.CollectionImageInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface CollectionImageInfoService {
    Page<CollectionImageInfo> find(Integer page, Integer count, String sort, String order);

    Page<CollectionImageInfo> findAllByCollectionId(
            Integer page, Integer count, String sort, UUID collectionId);
}
