package com.justyoga.blog.service.impl;

import com.justyoga.blog.domain.dao.BlogVideoRepo;
import com.justyoga.blog.domain.model.mysql.BlogVideo;
import com.justyoga.blog.service.interfaces.BlogVideoService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogVideoServiceImpl implements BlogVideoService {

    private final BlogVideoRepo blogVideoRepo;

    @Autowired
    public BlogVideoServiceImpl(BlogVideoRepo blogVideoRepo) {
        this.blogVideoRepo = blogVideoRepo;
    }

    @Override
    public BlogVideo findById(UUID id) {
        return blogVideoRepo.findById(id).orElse(null);
    }

    @Override
    public List<BlogVideo> findAllByBlogId(UUID blogId) {

        return blogVideoRepo.findAllByBlogId(blogId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BlogVideo save(BlogVideo entity) {
        return blogVideoRepo.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<BlogVideo> blogLike = blogVideoRepo.findById(id);
        if (blogLike.isPresent()) {
            blogVideoRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
