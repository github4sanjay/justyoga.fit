package com.justyoga.collection.domain.dao;

import com.justyoga.collection.domain.model.mysql.CollectionVideo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionVideoRepo extends JpaRepository<CollectionVideo, UUID> {

    List<CollectionVideo> findAllByCollectionId(UUID collectionId);

    List<CollectionVideo> deleteByCollectionId(UUID collectionId);

    Optional<CollectionVideo> findByCollectionIdAndVideoId(UUID collectionId, UUID videoId);
}
