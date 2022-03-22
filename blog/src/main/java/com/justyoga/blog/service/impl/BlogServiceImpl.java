package com.justyoga.blog.service.impl;

import com.justyoga.blog.domain.dao.BlogRepo;
import com.justyoga.blog.domain.model.mysql.Blog;
import com.justyoga.blog.service.interfaces.BlogService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    private final BlogRepo blogRepo;

    @Autowired
    public BlogServiceImpl(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    @Override
    public Blog findById(UUID id) {
        Optional<Blog> byId = blogRepo.findById(id);
        return byId.orElse(null);
    }

    @Override
    public boolean delete(UUID id) {
        Optional<Blog> repoById = blogRepo.findById(id);
        if (repoById.isPresent()) {
            blogRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Blog save(Blog blog) {
        return blogRepo.save(blog);
    }

    @Override
    public List<Blog> findAllByUserId(UUID placeId) {
        return blogRepo.findAllByUserId(placeId);
    }

    @Override
    public Page<Blog> findAllByUserId(Integer page, Integer count, String sort, UUID userId) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogRepo.findAllByUserId(userId, pageable.get());
    }

    @Override
    public Page<Blog> findAll(Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogRepo.findAll(pageable.get());
    }
}
