package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.CollectionInfo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionInfoRepository extends JpaRepository<CollectionInfo, UUID> {}
