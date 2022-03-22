package com.justyoga.collection.service.impl;

import com.justyoga.collection.domain.dao.CollectionRepo;
import com.justyoga.collection.domain.model.mysql.Collection;
import com.justyoga.collection.service.interfaces.CollectionService;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepo collectionRepo;

    @Autowired
    public CollectionServiceImpl(CollectionRepo collectionRepo) {
        this.collectionRepo = collectionRepo;
    }

    @Override
    public Collection findById(UUID id) {
        return collectionRepo.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<Collection> repoById = collectionRepo.findById(id);
        if (repoById.isPresent()) {
            collectionRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Collection save(Collection collection) {
        return collectionRepo.save(collection);
    }

    @Override
    public Page<Collection> findAll(Integer page, Integer count, String sortBy, String orderBy) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return collectionRepo.findAll(pageable.get());
    }
}
