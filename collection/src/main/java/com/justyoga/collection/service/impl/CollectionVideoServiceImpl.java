package com.justyoga.collection.service.impl;

import com.justyoga.collection.domain.dao.CollectionVideoRepo;
import com.justyoga.collection.domain.model.mysql.CollectionVideo;
import com.justyoga.collection.service.interfaces.CollectionVideoService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionVideoServiceImpl implements CollectionVideoService {

    private final CollectionVideoRepo collectionVideoRepo;

    @Autowired
    public CollectionVideoServiceImpl(CollectionVideoRepo collectionVideoRepo) {
        this.collectionVideoRepo = collectionVideoRepo;
    }

    @Override
    public CollectionVideo findById(UUID id) {
        return collectionVideoRepo.findById(id).orElse(null);
    }

    @Override
    public CollectionVideo findByCollectionIdAndVideoId(UUID collectionId, UUID videoId) {
        return collectionVideoRepo.findByCollectionIdAndVideoId(collectionId, videoId).orElse(null);
    }

    @Override
    public List<CollectionVideo> findAllByCollectionId(UUID reviewId) {
        return collectionVideoRepo.findAllByCollectionId(reviewId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CollectionVideo save(CollectionVideo collectionVideo) {
        return collectionVideoRepo.save(collectionVideo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<CollectionVideo> reviewLike = collectionVideoRepo.findById(id);
        if (reviewLike.isPresent()) {
            collectionVideoRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteByCollectionId(UUID collectionId) {
        List<CollectionVideo> collectionVideos =
                collectionVideoRepo.deleteByCollectionId(collectionId);
        return collectionVideos.size();
    }
}
