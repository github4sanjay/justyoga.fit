package com.justyoga.collection.domain.dao;

import com.justyoga.collection.domain.model.mysql.CollectionBlog;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionBlogRepo extends JpaRepository<CollectionBlog, UUID> {

    List<CollectionBlog> findAllByCollectionId(UUID collectionId);

    List<CollectionBlog> deleteByCollectionId(UUID collectionId);

    Optional<CollectionBlog> findByCollectionIdAndBlogId(UUID collectionId, UUID blogId);
}
