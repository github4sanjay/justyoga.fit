package com.justyoga.blog.service.impl;

import com.justyoga.blog.domain.dao.BlogLikeRepo;
import com.justyoga.blog.domain.model.mysql.BlogLike;
import com.justyoga.blog.service.interfaces.BlogLikeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogLikeServiceImpl implements BlogLikeService {

    private final BlogLikeRepo blogLikeRepo;

    @Autowired
    public BlogLikeServiceImpl(BlogLikeRepo blogLikeRepo) {
        this.blogLikeRepo = blogLikeRepo;
    }

    @Override
    public BlogLike findById(UUID id) {
        Optional<BlogLike> byId = blogLikeRepo.findById(id);
        return byId.orElse(null);
    }

    @Override
    public List<BlogLike> findAllByBlogId(UUID blogId) {
        return blogLikeRepo.findAllByBlogId(blogId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BlogLike save(BlogLike dto) {
        return blogLikeRepo.saveAndFlush(dto);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<BlogLike> blogLike = blogLikeRepo.findById(id);
        if (blogLike.isPresent()) {
            blogLikeRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
