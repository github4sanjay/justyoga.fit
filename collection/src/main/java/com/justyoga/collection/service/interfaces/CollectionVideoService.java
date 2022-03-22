package com.justyoga.collection.service.interfaces;

import com.justyoga.collection.domain.model.mysql.CollectionVideo;
import java.util.List;
import java.util.UUID;

public interface CollectionVideoService {
    CollectionVideo findById(UUID id);

    CollectionVideo findByCollectionIdAndVideoId(UUID collectionId, UUID videoId);

    List<CollectionVideo> findAllByCollectionId(UUID reviewId);

    CollectionVideo save(CollectionVideo entity);

    boolean delete(UUID id);

    Integer deleteByCollectionId(UUID collectionId);
}
