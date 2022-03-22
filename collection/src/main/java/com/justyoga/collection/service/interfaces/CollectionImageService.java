package com.justyoga.collection.service.interfaces;

import com.justyoga.collection.domain.model.mysql.CollectionImage;
import java.util.List;
import java.util.UUID;

public interface CollectionImageService {
    CollectionImage findById(UUID id);

    CollectionImage findByCollectionIdAndImageId(UUID collectionId, UUID imageId);

    List<CollectionImage> findAllByCollectionId(UUID reviewId);

    CollectionImage save(CollectionImage entity);

    boolean delete(UUID id);

    Integer deleteByCollectionId(UUID collectionId);
}
