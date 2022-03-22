package com.justyoga.collection.service.impl;

import com.justyoga.collection.domain.dao.CollectionBlogRepo;
import com.justyoga.collection.domain.model.mysql.CollectionBlog;
import com.justyoga.collection.service.interfaces.CollectionBlogService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionBlogServiceImpl implements CollectionBlogService {

    private final CollectionBlogRepo collectionBlogRepo;

    @Autowired
    public CollectionBlogServiceImpl(CollectionBlogRepo collectionBlogRepo) {
        this.collectionBlogRepo = collectionBlogRepo;
    }

    @Override
    public CollectionBlog findById(UUID id) {
        return collectionBlogRepo.findById(id).orElse(null);
    }

    @Override
    public CollectionBlog findByCollectionIdAndBlogId(UUID collectionId, UUID blogId) {
        return collectionBlogRepo.findByCollectionIdAndBlogId(collectionId, blogId).orElse(null);
    }

    @Override
    public List<CollectionBlog> findAllByCollectionId(UUID collectionId) {
        return collectionBlogRepo.findAllByCollectionId(collectionId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CollectionBlog save(CollectionBlog collectionBlog) {
        return collectionBlogRepo.save(collectionBlog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<CollectionBlog> collectionBlog = collectionBlogRepo.findById(id);
        if (collectionBlog.isPresent()) {
            collectionBlogRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteByCollectionId(UUID collectionId) {
        List<CollectionBlog> collectionBlogs =
                collectionBlogRepo.deleteByCollectionId(collectionId);
        return collectionBlogs.size();
    }
}
