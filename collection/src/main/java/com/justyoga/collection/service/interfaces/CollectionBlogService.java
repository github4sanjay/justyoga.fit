package com.justyoga.collection.service.interfaces;

import com.justyoga.collection.domain.model.mysql.CollectionBlog;
import java.util.List;
import java.util.UUID;

public interface CollectionBlogService {
    CollectionBlog findById(UUID id);

    CollectionBlog findByCollectionIdAndBlogId(UUID collectionId, UUID blogId);

    List<CollectionBlog> findAllByCollectionId(UUID reviewId);

    CollectionBlog save(CollectionBlog entity);

    boolean delete(UUID id);

    Integer deleteByCollectionId(UUID collectionId);
}
