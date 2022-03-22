package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.CollectionVideoInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionVideoInfoRepository extends JpaRepository<CollectionVideoInfo, UUID> {
    Page<CollectionVideoInfo> findAllByCollectionId(UUID collectionId, Pageable pageable);
}
