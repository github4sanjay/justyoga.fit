package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.CollectionVideoInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface CollectionVideoInfoService {
    Page<CollectionVideoInfo> find(Integer page, Integer count, String sort, String order);

    Page<CollectionVideoInfo> findAllByCollectionId(
            Integer page, Integer count, String sort, UUID collectionId);
}
