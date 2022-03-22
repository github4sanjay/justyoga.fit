package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.CollectionBlogInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionBlogInfoRepository extends JpaRepository<CollectionBlogInfo, UUID> {
    Page<CollectionBlogInfo> findAllByCollectionId(UUID collectionId, Pageable pageable);
}
