package com.justyoga.search.service.impl;

import com.justyoga.search.domain.dao.CollectionImageInfoRepository;
import com.justyoga.search.domain.model.CollectionImageInfo;
import com.justyoga.search.service.interfaces.CollectionImageInfoService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CollectionImageInfoServiceImpl implements CollectionImageInfoService {
    private final CollectionImageInfoRepository collectionImageInfoRepository;

    @Autowired
    public CollectionImageInfoServiceImpl(
            CollectionImageInfoRepository collectionImageInfoRepository) {
        this.collectionImageInfoRepository = collectionImageInfoRepository;
    }

    @Override
    public Page<CollectionImageInfo> find(
            Integer page, Integer count, String sortBy, String orderBy) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return collectionImageInfoRepository.findAll(pageable.get());
    }

    @Override
    public Page<CollectionImageInfo> findAllByCollectionId(
            Integer page, Integer count, String sort, UUID collectionId) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return collectionImageInfoRepository.findAllByCollectionId(collectionId, pageable.get());
    }
}
