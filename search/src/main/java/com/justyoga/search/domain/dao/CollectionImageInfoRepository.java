package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.CollectionImageInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionImageInfoRepository extends JpaRepository<CollectionImageInfo, UUID> {
    Page<CollectionImageInfo> findAllByCollectionId(UUID collectionId, Pageable pageable);
}
