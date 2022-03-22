package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.CollectionInfo;
import org.springframework.data.domain.Page;

public interface CollectionInfoService {
    Page<CollectionInfo> findAll(Integer page, Integer count, String sortBy, String orderBy);
}
