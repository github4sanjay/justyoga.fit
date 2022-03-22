package com.justyoga.collection.domain.dao;

import com.justyoga.collection.domain.model.mysql.CollectionImage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionImageRepo extends JpaRepository<CollectionImage, UUID> {

    List<CollectionImage> findAllByCollectionId(UUID collectionId);

    List<CollectionImage> deleteByCollectionId(UUID collectionId);

    Optional<CollectionImage> findByCollectionIdAndImageId(UUID collectionId, UUID imageId);
}
