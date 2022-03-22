package com.justyoga.collection.service.impl;

import com.justyoga.collection.domain.dao.CollectionImageRepo;
import com.justyoga.collection.domain.model.mysql.CollectionImage;
import com.justyoga.collection.service.interfaces.CollectionImageService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionImageServiceImpl implements CollectionImageService {

    private final CollectionImageRepo collectionImageRepo;

    @Autowired
    public CollectionImageServiceImpl(CollectionImageRepo collectionImageRepo) {
        this.collectionImageRepo = collectionImageRepo;
    }

    @Override
    public CollectionImage findById(UUID id) {
        return collectionImageRepo.findById(id).orElse(null);
    }

    @Override
    public CollectionImage findByCollectionIdAndImageId(UUID collectionId, UUID imageId) {
        return collectionImageRepo.findByCollectionIdAndImageId(collectionId, imageId).orElse(null);
    }

    @Override
    public List<CollectionImage> findAllByCollectionId(UUID collectionId) {
        return collectionImageRepo.findAllByCollectionId(collectionId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CollectionImage save(CollectionImage collectionImage) {
        return collectionImageRepo.save(collectionImage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<CollectionImage> reviewLike = collectionImageRepo.findById(id);
        if (reviewLike.isPresent()) {
            collectionImageRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteByCollectionId(UUID collectionId) {
        List<CollectionImage> collectionVideos =
                collectionImageRepo.deleteByCollectionId(collectionId);
        return collectionVideos.size();
    }
}
