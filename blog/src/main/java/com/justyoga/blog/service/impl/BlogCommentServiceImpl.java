package com.justyoga.blog.service.impl;

import com.justyoga.blog.domain.dao.BlogCommentRepo;
import com.justyoga.blog.domain.model.mysql.BlogComment;
import com.justyoga.blog.service.interfaces.BlogCommentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    private final BlogCommentRepo blogCommentRepo;

    @Autowired
    public BlogCommentServiceImpl(BlogCommentRepo blogCommentRepo) {
        this.blogCommentRepo = blogCommentRepo;
    }

    @Override
    public BlogComment findById(UUID id) {
        return blogCommentRepo.findById(id).orElse(null);
    }

    @Override
    public List<BlogComment> findAllByBlogId(UUID blogId) {
        return blogCommentRepo.findAllByBlogId(blogId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BlogComment save(BlogComment blogComment) {
        return blogCommentRepo.saveAndFlush(blogComment);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<BlogComment> blogLike = blogCommentRepo.findById(id);
        if (blogLike.isPresent()) {
            blogCommentRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
