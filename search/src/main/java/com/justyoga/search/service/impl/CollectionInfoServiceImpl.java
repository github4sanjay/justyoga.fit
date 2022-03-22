package com.justyoga.search.service.impl;

import com.justyoga.search.domain.dao.CollectionInfoRepository;
import com.justyoga.search.domain.model.CollectionInfo;
import com.justyoga.search.service.interfaces.CollectionInfoService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CollectionInfoServiceImpl implements CollectionInfoService {
    private final CollectionInfoRepository collectionInfoRepository;

    @Autowired
    public CollectionInfoServiceImpl(CollectionInfoRepository collectionInfoRepository) {
        this.collectionInfoRepository = collectionInfoRepository;
    }

    @Override
    public Page<CollectionInfo> findAll(
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
        return collectionInfoRepository.findAll(pageable.get());
    }
}
