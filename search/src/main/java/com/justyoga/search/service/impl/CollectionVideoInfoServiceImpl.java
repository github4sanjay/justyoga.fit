package com.justyoga.search.service.impl;

import com.justyoga.search.domain.dao.CollectionVideoInfoRepository;
import com.justyoga.search.domain.model.CollectionVideoInfo;
import com.justyoga.search.service.interfaces.CollectionVideoInfoService;
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
public class CollectionVideoInfoServiceImpl implements CollectionVideoInfoService {
    private final CollectionVideoInfoRepository collectionVideoInfoRepository;

    @Autowired
    public CollectionVideoInfoServiceImpl(
            CollectionVideoInfoRepository collectionVideoInfoRepository) {
        this.collectionVideoInfoRepository = collectionVideoInfoRepository;
    }

    @Override
    public Page<CollectionVideoInfo> find(
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
        return collectionVideoInfoRepository.findAll(pageable.get());
    }

    @Override
    public Page<CollectionVideoInfo> findAllByCollectionId(
            Integer page, Integer count, String sort, UUID collectionId) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return collectionVideoInfoRepository.findAllByCollectionId(collectionId, pageable.get());
    }
}
