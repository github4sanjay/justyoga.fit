package com.justyoga.blog.service.impl;

import com.justyoga.blog.domain.dao.BlogImageRepo;
import com.justyoga.blog.domain.model.mysql.BlogImage;
import com.justyoga.blog.service.interfaces.BlogImageService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogImageServiceImpl implements BlogImageService {

    private final BlogImageRepo blogImageRepo;

    @Autowired
    public BlogImageServiceImpl(BlogImageRepo blogImageRepo) {
        this.blogImageRepo = blogImageRepo;
    }

    @Override
    public BlogImage findById(UUID id) {
        return blogImageRepo.findById(id).orElse(null);
    }

    @Override
    public List<BlogImage> findAllByBlogId(UUID blogId) {
        return blogImageRepo.findAllByBlogId(blogId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BlogImage save(BlogImage blogImage) {
        return blogImageRepo.save(blogImage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<BlogImage> saveAll(Collection<BlogImage> blogImageMappings) {
        return blogImageRepo.saveAll(blogImageMappings);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<BlogImage> blogLike = blogImageRepo.findById(id);
        if (blogLike.isPresent()) {
            blogImageRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
